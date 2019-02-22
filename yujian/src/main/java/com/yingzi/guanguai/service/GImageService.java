package com.yingzi.guanguai.service;

import com.yingzi.guanguai.model.GImage;

import java.util.List;

public interface GImageService {

    int insert(GImage gImage);

    List<GImage> selectByUserId(long userId);

    int delByUserIdAndUrl(GImage gImage);
}
