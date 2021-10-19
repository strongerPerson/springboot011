package com.wt.service;

import com.wt.utils.FtpUtil;
import com.wt.utils.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
@Service
@Slf4j
public class NginxService {
    private static String newName=null;

    public Object uploadPicture(MultipartFile uploadFile,String newName1) {
        FtpUtil ftpUtil=new FtpUtil();
        System.out.println(newName1+"新名字文件");
        //1.3生成文件在服务器端存储的子目录
//        String filePath = new DateTime().toString("/yyyyMMdd/");
//        System.out.println(filePath+"子目录");

        //2、把图片上传到图片服务器
        //2.1获取上传的io流
        InputStream input = null;
        try {
            input = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //2.2调用FtpUtil工具类进行上传
        return ftpUtil.putImages(input, newName1,uploadFile);
    }
    //外部获取文件新名字、
    public static String getnewName(MultipartFile uploadFile) {
        //1、给上传的图片生成新的文件名
        //1.1获取原始文件名
        String oldName = uploadFile.getOriginalFilename();
        //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
        newName = IDUtils.genImageName();
        assert oldName != null;
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        System.out.println(newName+"我是测试类中的newName");

        return newName;
    }
}
