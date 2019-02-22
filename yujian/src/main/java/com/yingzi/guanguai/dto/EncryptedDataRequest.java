package com.yingzi.guanguai.dto;

import lombok.Data;

@Data
public class EncryptedDataRequest {
    String code;
    String encryptedData;
    String iv;
}
