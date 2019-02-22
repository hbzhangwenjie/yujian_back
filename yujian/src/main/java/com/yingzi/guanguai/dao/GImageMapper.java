package com.yingzi.guanguai.dao;

import com.yingzi.guanguai.model.GImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GImageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GImage record);

    GImage selectByPrimaryKey(Long id);

    List<GImage> selectAll();

    int updateByPrimaryKey(GImage record);

    List<GImage> selectByUserId(@Param("userId") long userId);

    int deleteByUserIdAndUrl(GImage record);
}