package com.yingzi.guanguai;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;



@RunWith(SpringRunner.class)
@SpringBootTest

public class GuanguaiApplicationTests {


    @Test
    public void contextLoads() throws IOException {
      /*  List<FileObjectSummary> list = fileService.list("grab-fusion-aws","");*/

 /*       InputStream i = fileService.dowloadFile("grab-fusion-aws","application-dev.txt");
        String content = "grab-fusion-aws";
    List<FileObjectSummary> list = fileService.list("grab-fusion-aws","");
        String s= fileService.uploadFile("grab-fusion-aws",i,"t4.txt","text/plain");
        System.out.println(s);
        fileService.deleteFile("grab-fusion-aws","t3.txt");
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        URL url = fileService.generatePresignedUrl("grab-fusion-aws","application-dev.txt",expiration);
        System.out.println(url);*/

    }

}
