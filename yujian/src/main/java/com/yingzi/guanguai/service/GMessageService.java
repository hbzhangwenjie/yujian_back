package com.yingzi.guanguai.service;

import com.yingzi.guanguai.model.GMessage;

import java.util.List;

public interface GMessageService {
    GMessage getLastGMessageBySessinId(long gSessionId);

    int countUnRead(long gSessionId, long userId);

    List<GMessage> getGMessageBySessinId(long gSessionId);

    int addMessage(GMessage gMessage);

    int readMessage(long gSessionId, long userId);


}
