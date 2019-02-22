package com.yingzi.guanguai.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingzi.guanguai.dao.GUserMapper;
import com.yingzi.guanguai.model.GUser;
import com.yingzi.guanguai.service.GUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("gUserService")
public class GUserServiceImpl implements GUserService {
    @Resource
    GUserMapper gUserMapper;
    @Override
    public int intsertGUser(GUser gUser) {
        return gUserMapper.insert(gUser);
    }

    @Override
    public GUser getGUserByOpenid(String openid) {
        return gUserMapper.selectByOpenid(openid);
    }

    @Override
    public GUser getGUserById(long id) {
        return gUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(GUser gUser) {
        return gUserMapper.updateById(gUser);
    }

    @Override
    public PageInfo<GUser> showUser(long Id,int pageNo) {
        String orderBy = "update_time DESC";
        PageHelper.startPage(pageNo, 10,orderBy);
        List<GUser> configs = gUserMapper.showUser(Id);
        PageInfo<GUser> pageInfo = new PageInfo(configs);
        return pageInfo;
    }

    @Override
    public GUser getUserById(long userId) {
        return gUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void online(long userId) {
        gUserMapper.online(userId);
    }

    @Override
    public GUser login(String passWord, String eMail) {
        return gUserMapper.login(passWord,eMail);
    }

    @Override
    public GUser register(String avatarUrl, String passWord, String eMail) {
        GUser gUser =new GUser();
        gUser.setAvatarUrl(avatarUrl);
        gUser.setEMail(eMail);
        gUser.setPassWord(passWord);
        gUser.setNickName("匿名");
        gUser.setMaxImage(5);
        gUserMapper.insert(gUser);
        return gUser;
    }

    @Override
    public GUser findByMail(String mail) {
        return gUserMapper.findByMail(mail);
    }

    @Override
    public void addImageMax(long id) {
        gUserMapper.addImageMax(id);
    }

}
