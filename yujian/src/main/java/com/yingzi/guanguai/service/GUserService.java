package com.yingzi.guanguai.service;

import com.github.pagehelper.PageInfo;
import com.yingzi.guanguai.model.GUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GUserService {

    int intsertGUser(GUser gUser);

    GUser getGUserByOpenid(String openid);

    GUser getGUserById(long id);

    int updateById(GUser gUser);


    PageInfo<GUser> showUser(long userId, int pageNo);

    GUser getUserById(long userId);

    void online(long userId);

    GUser login(String passWord,String eMail);

    GUser register(String avatarUrl,String passWord,String eMail);

    GUser findByMail(String mail);

    void addImageMax(long id);

}
