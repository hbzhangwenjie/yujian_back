package com.yingzi.guanguai.model;

import lombok.Data;

import java.util.Date;
@Data
public class GUser {
    private Long userId;

    private String unionId;

    private String openId;

    private String nickName;

    private Byte gender;

    private String city;

    private String province;

    private String country;

    private String profession;

    private String school;

    private String avatarUrl;

    private Date createTime;

    private String introduce;

    private Date updateTime;

    private String passWord;

    private String eMail;

    private int maxImage;

}