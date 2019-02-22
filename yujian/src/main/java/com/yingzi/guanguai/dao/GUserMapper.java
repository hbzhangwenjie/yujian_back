package com.yingzi.guanguai.dao;

import com.yingzi.guanguai.model.GUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GUserMapper {
    int deleteByPrimaryKey(Long userid);

    int insert(GUser record);

    GUser selectByPrimaryKey(Long userid);

    List<GUser> selectAll();

    int updateByPrimaryKey(GUser record);

    GUser selectByOpenid(String openid);

    int updateById(GUser gUser);

    List<GUser> showUser(@Param("userId") long Id);

    int online(Long userId);

    GUser login(@Param("passWord") String passWord,@Param("eMail") String eMail);

    GUser findByMail(@Param("mail") String mail);

    void addImageMax(@Param("userId") long userId);
}