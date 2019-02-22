package com.yingzi.guanguai.model;

import lombok.Data;

import java.util.Date;

@Data
public class GImage {
    private Long id;

    private Long userid;

    private String imageurl;

    private Date createTime;


}