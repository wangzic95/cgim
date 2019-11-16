package com.wzc.im.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 朋友圈动态评论表
 * @author WANGZIC
 */
@Data
public class FunComment {
    @Id
    private String id;

    private String dynamicid;

    private Integer userid;

    private String username;

    private Date createtime;

    private String content;

    private Integer replyId;

    private String replyName;

}