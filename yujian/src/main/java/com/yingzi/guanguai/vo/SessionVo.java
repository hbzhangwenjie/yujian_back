package com.yingzi.guanguai.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SessionVo {
    long sessionId;
    long otherUserId;
    String otherName;
    String otherAvatarUrl;
    String lastMessage;
    Date time;
    int unRead;
}
