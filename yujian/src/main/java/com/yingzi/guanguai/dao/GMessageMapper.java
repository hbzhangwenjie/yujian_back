package com.yingzi.guanguai.dao;

import com.yingzi.guanguai.model.GMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GMessageMapper {
    int deleteByPrimaryKey(Long messageid);

    int insert(GMessage record);

    GMessage selectByPrimaryKey(Long messageid);

    List<GMessage> selectAll();

    int updateByPrimaryKey(GMessage record);

    List<GMessage> selectBySessionId(Long gSessionId);
   int countUnRead(@Param("gSessionId") Long gSessionId,@Param("userId") Long userId);

    int readMessage(@Param("gSessionId") Long gSessionId,@Param("userId") Long userId);
}