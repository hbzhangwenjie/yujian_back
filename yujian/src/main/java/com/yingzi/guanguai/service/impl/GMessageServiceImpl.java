package com.yingzi.guanguai.service.impl;

import com.yingzi.guanguai.dao.GMessageMapper;
import com.yingzi.guanguai.model.GMessage;
import com.yingzi.guanguai.service.GMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("gMessageService")
public class GMessageServiceImpl implements GMessageService {
    @Resource
    GMessageMapper gMessageMapper;

    @Override
    public GMessage getLastGMessageBySessinId(long gSessionId) {
        List<GMessage> gMessages = gMessageMapper.selectBySessionId(gSessionId);
        if (gMessages.size()==0) return null;
        return gMessages.get(gMessages.size()-1);

    }

    @Override
    public int countUnRead(long gSessionId, long userId) {
        return gMessageMapper.countUnRead(gSessionId, userId);
    }

    @Override
    public List<GMessage> getGMessageBySessinId(long gSessionId) {
        return gMessageMapper.selectBySessionId(gSessionId);
    }

    @Override
    public int addMessage(GMessage gMessage) {
        return gMessageMapper.insert(gMessage);
    }

    @Override
    public int readMessage(long gSessionId, long userId) {
        return gMessageMapper.readMessage(gSessionId,userId);
    }


}
