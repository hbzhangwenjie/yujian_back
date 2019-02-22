package com.yingzi.guanguai.dto;

import lombok.Data;

@Data
public class AddMessageRequest {
    long sessionId;
    long userId;
    long toUserId;
    String message;
}
