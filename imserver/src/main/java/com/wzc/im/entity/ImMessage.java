package com.wzc.im.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 消息表
 * @author WANGZIC
 */
@Data
public class ImMessage {
    @Id
    private String mid;

    private Integer fromid;

    private String fromname;

    private String fromtype;

    private Integer targetid;

    private String targetname;

    private String targettype;

    private String msgtext;

    private String msgextras;

    private String msgtype;

    private Date msgtime;

    
}