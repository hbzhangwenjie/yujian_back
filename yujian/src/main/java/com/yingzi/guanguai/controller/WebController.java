package com.yingzi.guanguai.controller;

import com.yingzi.guanguai.config.QqUploadFile;
import com.yingzi.guanguai.constant.Constants;
import com.yingzi.guanguai.dto.BaseRequest;
import com.yingzi.guanguai.dto.BaseResponse;
import com.yingzi.guanguai.dto.LoginRequest;
import com.yingzi.guanguai.dto.UploadImageRequest;
import com.yingzi.guanguai.model.GImage;
import com.yingzi.guanguai.model.GUser;
import com.yingzi.guanguai.service.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@CrossOrigin
@Controller
@RequestMapping(value = "/api")
public class WebController {
    @Autowired
    GUserService gUserService;
    @Autowired
    GImageService gImageService;
    @Autowired
    GSessionService gSessionService;
    @Autowired
    GMessageService gMessageService;
    @Autowired
    GuggestionService guggestionService;
    @Value("${imageUrl}")
    String imageUrl;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody LoginRequest loginRequest) {
        BaseResponse<GUser> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.FAIL.getValue());
        GUser gUser = gUserService.login(loginRequest.getPassWord(), loginRequest.getMail());
        if (gUser != null) {
            baseResponse.setResult(Constants.SUCESS.getValue());
            baseResponse.setData(gUser);
            gUserService.online(gUser.getUserId());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResponse singleFileUpload(@RequestParam("file") MultipartFile file) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        if (file.isEmpty()) {
            baseResponse.setResult(Constants.FAIL.getValue());
            return baseResponse;
        }
        String result = QqUploadFile.fileUpload(file);
        if (result.equalsIgnoreCase(Constants.FAIL.getValue())) {
            baseResponse.setResult(Constants.FAIL.getValue());
        }
        baseResponse.setData(imageUrl + result);
        baseResponse.setResult(Constants.SUCESS.getValue());
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public BaseResponse imageUpload(@RequestBody UploadImageRequest uploadImageRequest) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.SUCESS.getValue());
        String images[] = uploadImageRequest.getImages().split(",");
        if (images.length < 0) {
            baseResponse.setResult(Constants.FAIL.getValue());
            baseResponse.setData("没发现图片");
        } else {
            for (String image : images) {
                GImage gImage = new GImage();
                gImage.setUserid(uploadImageRequest.getUserId());
                gImage.setImageurl(image);
                gImageService.insert(gImage);
            }
            GUser gUser = new GUser();
            gUser.setMaxImage(images.length);
            gUser.setUserId(uploadImageRequest.getUserId());
            gUserService.updateById(gUser);
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody LoginRequest loginRequest) {
        BaseResponse<GUser> baseResponse = new BaseResponse<>();
        Pattern pattern = Pattern.compile(Constants.REG.getValue());
        if (loginRequest.getMail() != null) {
            Matcher matcher = pattern.matcher(loginRequest.getMail());
            if (!matcher.matches()) {
                baseResponse.setResult("邮箱格式不正确！");
                return baseResponse;
            }
        }
        if (gUserService.findByMail(loginRequest.getMail()) != null) {
            baseResponse.setResult("邮箱已经被注册了！");
            return baseResponse;
        }

        GUser gUser = gUserService.register(loginRequest.getImg(), loginRequest.getPassWord(), loginRequest.getMail());
        if (gUser != null) {
            baseResponse.setResult(Constants.SUCESS.getValue());
            baseResponse.setData(gUser);
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/showImage", method = RequestMethod.POST)
    public BaseResponse showMyImage(@RequestBody BaseRequest baseRequest) {
        BaseResponse<GImage> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.SUCESS.getValue());
        baseResponse.setDatas(gImageService.selectByUserId(baseRequest.getUserId()));
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
    public BaseResponse deleteImage(@RequestBody UploadImageRequest uploadImageRequest) {
        BaseResponse<GImage> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.SUCESS.getValue());
        GImage gImage = new GImage();
        gImage.setImageurl(uploadImageRequest.getImages());
        gImage.setUserid(uploadImageRequest.getUserId());
        gImageService.delByUserIdAndUrl(gImage);
        gUserService.addImageMax(uploadImageRequest.getUserId());
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/uploadava", method = RequestMethod.POST)
    public BaseResponse uploadAva(@RequestBody UploadImageRequest uploadImageRequest) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.SUCESS.getValue());
        GUser gUser = new GUser();
        gUser.setUserId(uploadImageRequest.getUserId());
        gUser.setAvatarUrl(uploadImageRequest.getImages());
        gUserService.updateById(gUser);
        return baseResponse;
    }

}
