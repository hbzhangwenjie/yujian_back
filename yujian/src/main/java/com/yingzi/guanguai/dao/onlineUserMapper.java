package com.yingzi.guanguai.dao;

import com.yingzi.guanguai.model.onlineUser;
import java.util.List;

public interface onlineUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(onlineUser record);

    onlineUser selectByPrimaryKey(Long id);

    List<onlineUser> selectAll();

    int updateByPrimaryKey(onlineUser record);
}