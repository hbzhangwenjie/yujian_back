package com.yingzi.guanguai.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.yingzi.guanguai.constant.Constants;
import org.springframework.web.multipart.MultipartFile;

public class QqUploadFile {

    private static volatile COSClient cOSClient = null;

    private  QqUploadFile() {
    }

    private static COSClient getCosClient() {
        if(cOSClient == null){
            synchronized (QqUploadFile.class) {
                if (cOSClient == null) {
                    COSCredentials cred = new BasicCOSCredentials("", "");
                    ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
                    cOSClient = new COSClient(cred, clientConfig);
                }
            }
        }
        return cOSClient;
    }

    public static String fileUpload(MultipartFile file){
        String key =   System.currentTimeMillis() + file.getOriginalFilename();
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //小于10mb
            if(file.getSize()>10485760) return Constants.FAIL.getValue();
            objectMetadata.setContentLength(file.getSize());
            PutObjectRequest putObjectRequest = new PutObjectRequest("lg-287243fm-1254056081", key, file.getInputStream(), objectMetadata);
            QqUploadFile.getCosClient().putObject(putObjectRequest);
        } catch (Exception e) {
            return Constants.FAIL.getValue();
        }
        return key;
    }
}
