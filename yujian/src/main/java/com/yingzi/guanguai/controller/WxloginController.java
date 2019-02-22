package com.yingzi.guanguai.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yingzi.guanguai.constant.Constants;
import com.yingzi.guanguai.dto.*;
import com.yingzi.guanguai.model.GMessage;
import com.yingzi.guanguai.model.GSession;
import com.yingzi.guanguai.model.GUser;
import com.yingzi.guanguai.model.Guggestion;
import com.yingzi.guanguai.service.GMessageService;
import com.yingzi.guanguai.service.GSessionService;
import com.yingzi.guanguai.service.GUserService;
import com.yingzi.guanguai.service.GuggestionService;
import com.yingzi.guanguai.util.CacheMap;
import com.yingzi.guanguai.util.WxInfoDecode;
import com.yingzi.guanguai.vo.SessionVo;
import lombok.extern.java.Log;
import net.sf.ehcache.CacheManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Log
@CrossOrigin
@Controller
@RequestMapping(value = "/wx")
public class WxloginController {
    private final static String WXURL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    @Resource
    CacheManager ehCacheCacheManager;
    @Autowired
    GUserService gUserService;
    @Autowired
    GSessionService gSessionService;
    @Autowired
    GMessageService gMessageService;
    @Autowired
    GuggestionService guggestionService;


    @ResponseBody
    @RequestMapping(value = "/loginWx", method = RequestMethod.POST)
    public BaseResponse getWxUserInfo(@RequestBody EncryptedDataRequest encryptedDataRequest) {
        BaseResponse<GUser> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.SUCESS.getValue());
        String url = String.format(WXURL, Constants.APPID.getValue(), Constants.SECRET.getValue(), encryptedDataRequest.getCode());
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpclient = null;
        log.info("login request:" + url);
        try {
            httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String loginResponse = EntityUtils.toString(entity);
            log.info("login response" + entity);
            WxLoginResponse wxLoginResponse = JSON.parseObject(loginResponse, WxLoginResponse.class);
            EncryptedData encryptedData = WxInfoDecode.decodeUserInfo(wxLoginResponse.getSession_key(), encryptedDataRequest.getEncryptedData(), encryptedDataRequest.getIv());
            GUser gUser = gUserService.getGUserByOpenid(encryptedData.getOpenId());
            if (null == gUser) {
                //第一次進入小程序
                GUser gUser1 = new GUser();
                BeanUtils.copyProperties(encryptedData, gUser1);
                gUserService.intsertGUser(gUser1);
                gUser = gUser1;
                gUser.setMaxImage(5);
                gUser1 = null;
            }
            gUserService.online(gUser.getUserId());
            baseResponse.setData(gUser);
        } catch (Exception e) {
            baseResponse.setResult(Constants.FAIL.getValue());
        } finally {
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("httpclient关闭异常" + e);
                }
            }
        }
        return baseResponse;
    }

    @RequestMapping(value = "/checkSeesion", method = RequestMethod.GET)
    public String checkSession(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        //本地 不保存 seesion 依赖小程序管理session，小程序session过期我就认为过期
        return Constants.FAIL.getValue();
    }
    //审核外壳
    @ResponseBody
    @RequestMapping(value = "/tianqi", method = RequestMethod.GET)
    public BaseResponse tianqi(){
        BaseResponse<GUser> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.SUCESS.getValue());
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public BaseResponse getUser(@RequestParam(value = "sessionId") long sessionId) {
        BaseResponse<GUser> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.SUCESS.getValue());
        GUser gUser = gUserService.getGUserById(sessionId);
        if (null == gUser) {
            baseResponse.setResult(Constants.FAIL.getValue());
        }else{
            gUserService.online(gUser.getUserId());
        }
        baseResponse.setData(gUser);
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public BaseResponse updateUser(@RequestBody GUser gUser) {
        BaseResponse<GUser> baseResponse = new BaseResponse<>();
        int res = gUserService.updateById(gUser);
        if (res == 1) baseResponse.setResult(Constants.SUCESS.getValue());
        else baseResponse.setResult(Constants.FAIL.getValue());
        baseResponse.setData(gUser);
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/showUser", method = RequestMethod.POST)
    public BaseResponse showUser(@RequestBody ShowUserRequest showUserRequest) {
        BaseResponse<GUser> baseResponse = new BaseResponse();
        baseResponse.setResult(Constants.FAIL.getValue());
        if (isOurUser(showUserRequest.getUserId())) {
            PageInfo<GUser> gUserList = gUserService.showUser(showUserRequest.getUserId(), showUserRequest.getPageNo());
            baseResponse.setDatas(gUserList.getList());
            baseResponse.setResult(Constants.SUCESS.getValue());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/readMessage", method = RequestMethod.POST)
    public BaseResponse readMessage(@RequestBody ShowMessageRequest showMessageRequest) {
        BaseResponse<GUser> baseResponse = new BaseResponse();
        baseResponse.setResult(Constants.SUCESS.getValue());
        gMessageService.readMessage(showMessageRequest.getSessionId(), showMessageRequest.getUserId());
        CacheMap.del(showMessageRequest.getUserId());
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/showSession", method = RequestMethod.POST)
    public BaseResponse showSession(@RequestBody BaseRequest baseRequest) {
        BaseResponse<SessionVo> baseResponse = new BaseResponse();
        baseResponse.setResult(Constants.SUCESS.getValue());
        //根据用户信息去session表找到所有的会话
        List<GSession> gSessionList = gSessionService.selectByUserId(baseRequest.getUserId());
        //组装返回信息
        List<SessionVo> sessionVos = new ArrayList<>();
        for (GSession gSession : gSessionList) {
            GMessage gMessage = gMessageService.getLastGMessageBySessinId(gSession.getSessionid());
            if (gMessage == null) continue;
            SessionVo sessionVo = new SessionVo();
            sessionVo.setSessionId(gSession.getSessionid());
            GUser otherUser;
            if (baseRequest.getUserId() == gSession.getUseridone()) {
                otherUser = gUserService.getUserById(gSession.getUseridtwo());
            } else {
                otherUser = gUserService.getUserById(gSession.getUseridone());
            }
            sessionVo.setOtherAvatarUrl(otherUser.getAvatarUrl());
            sessionVo.setOtherName(otherUser.getNickName());
            sessionVo.setOtherUserId(otherUser.getUserId());
            int count = gMessageService.countUnRead(gSession.getSessionid(), baseRequest.getUserId());
            sessionVo.setLastMessage(gMessage.getMessage());
            sessionVo.setTime(gMessage.getCreateTime());
            sessionVo.setUnRead(count);
            sessionVos.add(sessionVo);
        }
        Collections.sort(sessionVos, (o1, o2) -> o2.getTime().compareTo(o1.getTime()));
        baseResponse.setDatas(sessionVos);
        return baseResponse;
    }

    @RequestMapping(value = "/showMessage", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse showMessage(@RequestBody ShowMessageRequest showMessageRequest) {
        BaseResponse<GMessage> baseResponse = new BaseResponse<>();
        List<GMessage> gMessages = gMessageService.getGMessageBySessinId(showMessageRequest.getSessionId());
        if (gMessages != null) {
            baseResponse.setDatas(gMessages);
        }
        baseResponse.setResult(Constants.SUCESS.getValue());
        return baseResponse;
    }

    @RequestMapping(value = "/addGuggestion", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addGuggestion(@RequestBody Guggestion guggestion) {
        BaseResponse<Guggestion> baseResponse = new BaseResponse<>();
        guggestionService.addGugestion(guggestion);
        baseResponse.setResult(Constants.SUCESS.getValue());
        return baseResponse;
    }

    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addMessage(@RequestBody AddMessageRequest addMessageRequest) {
        BaseResponse<GMessage> baseResponse = new BaseResponse<>();
        GMessage gMessage = new GMessage();
        gMessage.setMessage(addMessageRequest.getMessage());
        gMessage.setSessionid(addMessageRequest.getSessionId());
        gMessage.setUserid(addMessageRequest.getUserId());
        gMessage.setTouserid(addMessageRequest.getToUserId());
        int res = gMessageService.addMessage(gMessage);
        if (res == 1) {
            baseResponse.setResult(Constants.SUCESS.getValue());
            CacheMap.put(addMessageRequest.getToUserId(),Boolean.TRUE);
        } else {
            baseResponse.setResult(Constants.FAIL.getValue());
        }

        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/findSession", method = RequestMethod.POST)
    public BaseResponse findSession(@RequestBody AddMessageRequest addMessageRequest) {
        BaseResponse<Long> baseResponse = new BaseResponse();
        baseResponse.setResult(Constants.SUCESS.getValue());
        //先到数据库找sessionID 看他们之前有没有聊过
        Long sessionId = gSessionService.selectByUserAndToUser(Math.abs(addMessageRequest.getUserId() - addMessageRequest.getToUserId()), addMessageRequest.getUserId() + addMessageRequest.getToUserId());
        if (sessionId == null) {
            //没有聊过
            GSession gSession = new GSession();
            gSession.setUseridone(addMessageRequest.getUserId());
            gSession.setUseridtwo(addMessageRequest.getToUserId());
            long newSessionId = gSessionService.addSession(gSession);
            sessionId = newSessionId;
        }
        baseResponse.setData(sessionId);
        return baseResponse;
    }
    @ResponseBody
    @RequestMapping(value = "/getredPo", method = RequestMethod.GET)
    public BaseResponse getredPo(@RequestParam(value = "sessionId") long sessionId) {
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.SUCESS.getValue());
        if(null != CacheMap.get(sessionId)){
            baseResponse.setData(Boolean.TRUE);
        }else{
            baseResponse.setData(Boolean.FALSE);
        }
        return baseResponse;
    }
    private boolean isOurUser(long Id) {
        GUser gUser = gUserService.getGUserById(Id);
        return gUser == null ? false : true;
    }
}
