package com.wzc.im.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 朋友圈动态表
 * @author WANGZIC
 */
@Data
public class FunDynamic {
    @Id
    private String id;

    private Integer createuserid;

    private String createname;

    private Date createtime;

    private String content;

    private String extras;

    private String likeUserids;

    private String likeNames;

}