package com.wt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wt.pojo.File;

import com.wt.service.FileService;

import com.wt.service.NginxService;
import com.wt.utils.FtpUtil;
import com.wt.utils.ResultCode;
import com.wt.utils.ResultCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/file")
@Slf4j
@CrossOrigin
public class FileController {

    @Autowired
    FileService  fileService;
    @Autowired
    NginxService nginxService;
    //文件上传
    @PostMapping("/upload")
    public String addFile(@RequestParam(value = "file") MultipartFile uploadFile) {
        long begin = System.currentTimeMillis();
        File file = new File();
        FtpUtil ftpUtil=new FtpUtil();
        Object result = null;
        try {
            if (!uploadFile.isEmpty()) {
                //讲图片地址写入数据中
                //1，存入图片名字
                file.setFilename(NginxService.getnewName(uploadFile));
                //   model.addAttribute("name",file.getFilename());
                //2.存入图片地址、
                //2.1根目录
                String rootPath = ftpUtil.getRootPath();
                //2.2服务器路径
                String imgUrl = ftpUtil.getImgUrl();
                System.out.println("返回nginx路径"+imgUrl);
                file.setAddress(imgUrl+file.getFilename());
                System.out.println(file.getAddress()+"设置的文件访问路径——file");
                result = nginxService.uploadPicture(uploadFile,file.getFilename());
                System.out.println(result+"文件存储在Nginx中的路劲");

                boolean save = fileService.save(file);
                if (save) {
                    log.info("保存成功");
                } else {
                    log.info("保存失败");
                }
            }else {
                log.info("文件为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("任务结束，共耗时：[" + (end - begin) + "]毫秒");
       return "file";
    }
   // 获取问价来自数据库
    @PostMapping("/ById")
    public String queryById( Model mav,HttpServletRequest request){
        String id = request.getParameter("id");
        //获取到查询的数据
        File byId = fileService.getById(id);
        String src = byId.getAddress();
        // //将数据放置到ModelAndView对象view中,第二个参数可以是任何java类型
        mav.addAttribute("file", src);
        return"fileShow";
    }


}
