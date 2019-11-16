package com.wzc.im.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.wzc.im.entity.ImMessage;
import com.wzc.im.entity.ImUser;
import com.wzc.im.common.utils.Imt;
import com.wzc.im.service.ImGroupService;
import com.wzc.im.service.ImMessageService;
import com.wzc.im.service.ImUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token为前端连接时的标识，后端根据此token维护用户与webSocket的绑定
 * @author WANGZIC
 */
@Slf4j
@Component
@ServerEndpoint("/wsim/{token}")
public class WebSocketServer {

    @Autowired
    public static ImMessageService messageService;

    @Autowired
    public static ImGroupService groupService;

    @Autowired
    public static ImUserService userService;

    private static final String SINGLE_CHAT="1";

    private static final String GROUP_CHAT="2";

    private static final String ALL_USER="3";

    private static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 连接打开
     * @param token
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam(value = "token") String token, Session session) throws IOException {
        if(webSocketMap.containsKey(token)){
            webSocketMap.get(token).close();
        }
        webSocketMap.put(token, session);
        log.info("用户{}上线, 当前在线人数{}",token, webSocketMap.size());
    }

    /**
     * 连接关闭
     * @param token
     */
    @OnClose
    public void onClose(@PathParam(value = "token") String token) {
        webSocketMap.remove(token);
        log.info("用户{}下线, 当前在线人数{}",token, webSocketMap.size());
    }
    /**
     * 连接出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        if (session.isOpen()) {
            session.close();
        }
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId(),error);
    }

    /**
     * 接收消息
     * @param messageText 消息文本
     * @param session 发送人会话
     */
    @OnMessage
    public void onMessage(String messageText, Session session) {
        log.info("get message: " + messageText);
        ImMessage msg = JSON.parseObject(messageText, ImMessage.class);
        msg.setMid(IdUtil.simpleUUID());
        if(messageService.insert(msg)>0){
            if(SINGLE_CHAT.equals(msg.getTargettype())){
                sendMessageToUser(msg,msg.getTargetid()+"");
            }else if(GROUP_CHAT.equals(msg.getTargettype())){
                sendMessageToGroup(msg);
            }else if(ALL_USER.equals(msg.getTargettype())){
                sendMessageToAll(msg);
            }
        }
    }

    /**
     * 发送消息
     * @param session 接收人会话
     * @param message 消息实体
     */
    private static void sendMessage(Session session, ImMessage message) {
        if (session!= null) {
            try {
                String msgText = JSON.toJSONStringWithDateFormat(message,"yyyy-MM-dd HH:mm:ss");
                session.getBasicRemote().sendText(msgText);
            } catch (IOException e) {
                log.error("消息发送失败！",e.getMessage(),e);
            }
        }
    }

    /**
     * 给指定用户发消息（单聊）
     */
    private void sendMessageToUser(ImMessage msg, String tid) {
        if(webSocketMap.containsKey(tid) && webSocketMap.get(tid).isOpen()){
            sendMessage(webSocketMap.get(tid),msg);
        }else{
            int uid = Integer.parseInt(tid);
            ImUser user =userService.selectByPrimaryKey(uid);
            String idstr =user.getOfflinelogs();
            if(StringUtils.isNotEmpty(idstr)){
                user.setOfflinelogs(idstr+","+msg.getMid());
            }else{
                user.setOfflinelogs(msg.getMid());
            }
            userService.updateByPrimaryKey(user);
        }
    }
    /**
     * 给指定群组用户发消息（群聊）
     */
    private void sendMessageToGroup(ImMessage msg){
        String str =groupService.selectByPrimaryKey(msg.getTargetid()).getMembers();
        if (StringUtils.isNotEmpty(str)) {
            for(String tid:str.split(",")){
                if(!tid.equals(msg.getFromid().toString())){
                    sendMessageToUser(msg,tid);
                }
            }
        }
    }

    /**
     * 给所有在线人员发消息（通知）
     * @param msg 消息实体
     * @param session 当前登录人会话
     */
    private void sendMessageToAll(ImMessage msg) {
        for (String key : webSocketMap.keySet()) {
            Session target = webSocketMap.get(key);
            if (!key.equals(msg.getFromid().toString()) && target.isOpen()) {
                sendMessage(target,msg);
            }
        }
    }
}