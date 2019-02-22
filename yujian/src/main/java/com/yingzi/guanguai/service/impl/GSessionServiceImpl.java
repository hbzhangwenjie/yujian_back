package com.yingzi.guanguai.service.impl;

import com.yingzi.guanguai.dao.GSessionMapper;
import com.yingzi.guanguai.model.GSession;
import com.yingzi.guanguai.service.GSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("gSessionService")
public class GSessionServiceImpl implements GSessionService {

    @Resource
    GSessionMapper gSessionMapper;
    @Override
    public List<GSession> selectByUserId(long userId) {
        return gSessionMapper.selectByUserId(userId);
    }

    @Override
    public long addSession(GSession gSession) {
         gSessionMapper.insert(gSession);
        return gSession.getSessionid();
    }

    @Override
    public Long selectByUserAndToUser(long sub, long add) {
        return gSessionMapper.selectByUserAndToUser(sub,add);
    }
}
