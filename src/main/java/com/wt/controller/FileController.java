package com.wt.controller;

import com.wt.pojo.File;

import com.wt.service.FileService;

import com.wt.service.NginxService;
import com.wt.utils.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
        File file = new File();
        FtpUtil ftpUtil=new FtpUtil();
        try {
            if (!uploadFile.isEmpty()) {
                //1，讲图片地址写入数据中
                //1.1，存入图片名字
                file.setFilename(NginxService.getnewName(uploadFile));
                //1.2.存入图片地址（服务器路径）
                file.setAddress(ftpUtil.getImgUrl()+file.getFilename());
                //2，将图片上传至Nginx
                nginxService.uploadPicture(uploadFile,file.getFilename());
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
