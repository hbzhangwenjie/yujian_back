package com.yingzi.guanguai.dto;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class EncryptedData {

    private String openId;
    private String nickName;
    private Byte gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    private watermark watermark;

    @Data
    class watermark {
        String appid;
        Timestamp timestamp;
    }

}
