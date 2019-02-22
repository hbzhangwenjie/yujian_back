package com.yingzi.guanguai.dto;

import lombok.Data;

@Data
public class WxLoginResponse {
    String openid;
    String session_key;
    String unionid;
    long expires_in;
}
