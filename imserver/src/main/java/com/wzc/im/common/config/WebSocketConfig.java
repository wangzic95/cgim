package com.wzc.im.common.config;

import com.wzc.im.controller.WebSocketServer;
import com.wzc.im.service.ImGroupService;
import com.wzc.im.service.ImMessageService;
import com.wzc.im.service.ImUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启WebSocket支持
 * @author WANGZIC
 */
@Configuration  
public class WebSocketConfig {
	
    @Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    }

    @Autowired
    public void setMessageService(ImMessageService messageService) {
        WebSocketServer.messageService = messageService;
    }

    @Autowired
    public void setGroupService(ImGroupService groupService) {
        WebSocketServer.groupService = groupService;
    }

    @Autowired
    public void setUserService(ImUserService userService) {
        WebSocketServer.userService = userService;
    }

}