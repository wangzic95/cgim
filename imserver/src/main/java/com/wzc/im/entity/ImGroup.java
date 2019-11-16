package com.wzc.im.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 群组表
 * @author WANGZIC
 */
@Data
public class ImGroup {
    @Id
    private Integer gid;

    private String gimg;

    private String gname;

    private String members;

    private Date createtime;

    private Integer createuserid;

    private String description;

    private String createname;

}