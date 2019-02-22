package com.yingzi.guanguai.model;

import lombok.Data;

import java.util.Date;
@Data
public class GSession {
    private Long sessionid;

    private Long useridone;

    private Long useridtwo;

    private Date updateTime;


}