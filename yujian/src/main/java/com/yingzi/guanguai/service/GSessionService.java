package com.yingzi.guanguai.service;

import com.yingzi.guanguai.model.GSession;

import java.util.List;

public interface GSessionService {
    List<GSession> selectByUserId(long userId);

    long addSession(GSession gSession);

    Long selectByUserAndToUser(long sub, long add);
}
