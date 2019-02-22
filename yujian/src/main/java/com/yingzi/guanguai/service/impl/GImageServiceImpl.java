package com.yingzi.guanguai.service.impl;

import com.yingzi.guanguai.dao.GImageMapper;
import com.yingzi.guanguai.model.GImage;
import com.yingzi.guanguai.service.GImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("gImageService")
public class GImageServiceImpl implements GImageService {
    @Resource
    GImageMapper gImageMapper;

    @Override
    public int insert(GImage gImage) {
        return gImageMapper.insert(gImage);
    }

    @Override
    public List<GImage> selectByUserId(long userId) {
        return gImageMapper.selectByUserId(userId);
    }

    @Override
    public int delByUserIdAndUrl(GImage gImage) {
        return gImageMapper.deleteByUserIdAndUrl(gImage);
    }
}
