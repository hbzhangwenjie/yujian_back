package com.yingzi.guanguai.dao;

import com.yingzi.guanguai.model.GSession;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GSessionMapper {
    int deleteByPrimaryKey(Long sessionid);

    int insert(GSession record);

    GSession selectByPrimaryKey(Long sessionid);

    List<GSession> selectAll();

    int updateByPrimaryKey(GSession record);

    List<GSession> selectByUserId(long userID);

    Long selectByUserAndToUser(@Param("sub") Long sub , @Param("add") Long add);

}