package com.yingzi.guanguai.service.impl;

import com.yingzi.guanguai.dao.GMessageMapper;
import com.yingzi.guanguai.dao.GuggestionMapper;
import com.yingzi.guanguai.model.Guggestion;
import com.yingzi.guanguai.service.GuggestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("guggestionService")
public class GuggestionServiceImpl implements GuggestionService {
    @Resource
    GuggestionMapper guggestionMapper;

    @Override
    public int addGugestion(Guggestion guggestion) {
        return guggestionMapper.insert(guggestion);
    }
}
