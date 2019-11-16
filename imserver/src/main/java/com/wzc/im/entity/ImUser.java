package com.wzc.im.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 用户信息表
 * @author WANGZIC
 */
@Data
public class ImUser {
    @Id
    private Integer id;

    private String uimg;

    private String name;

    private String remarkname;

    private String password;

    private String cellphone;

    private Date birthday;

    private String gender;

    private String age;

    private String address;

    private String email;

    private String hobby;

    private String signature;

    private String friends;

    private String groups;

    private String talklist;

    private Date onlinetime;

    private Date offlinetime;

    private String scrap;

    private String offlinelogs;

}