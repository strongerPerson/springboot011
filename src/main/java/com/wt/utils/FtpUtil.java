package com.wt.utils;

import com.jcraft.jsch.*;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

@PropertySource({"classpath:application.yml"})
@Component
public class FtpUtil {
       // private static Logger logger = (Logger) LoggerFactory.getLogger(FtpUtil.class);

        /**
         * ftp服务器ip地址
         */
        private static String host;

        @Value("${ftp.host}")
        public void setHost(String val){
            FtpUtil.host = val;
        }

        /**
         * 端口
         */
        private static int port;

        @Value("${ftp.port}")
        public void setPort(int val){
            FtpUtil.port = val;
        }

        /**
         * 用户名
         */
        private static String userName;

        @Value("${ftp.userName}")
        public void setUserName(String val){
            FtpUtil.userName = val;
        }

        /**
         * 密码
         */
        private static String password;

        @Value("${ftp.password}")
        public void setPassword(String val){
            FtpUtil.password = val;
        }

        /**
         * 存放图片的根目录
         */
        private static String rootPath;

        @Value("${ftp.rootPath}")
        public void setRootPath(String val){
            FtpUtil.rootPath = val;
        }

        public static String getRootPath(){
            return FtpUtil.rootPath;
        }

        /**
         * 存放图片的路径
         */
        private static String imgUrl;

        @Value("${ftp.img.url}")
        public void setImgUrl(String val){
            FtpUtil.imgUrl = val;
        }
        public static String getImgUrl(){
            return FtpUtil.imgUrl;
        }

        /**
         * 获取连接
         */
        private static ChannelSftp getChannel() throws Exception{
            JSch jsch = new JSch();

            //->ssh root@host:port
            Session sshSession = jsch.getSession(userName,host,port);
            //密码
            sshSession.setPassword(password);

            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();

            Channel channel = sshSession.openChannel("sftp");
            channel.connect();

            return (ChannelSftp) channel;
        }

        /**
         * ftp上传图片
         * @param inputStream 图片io流
         * @param imagesName 图片名称
         * @return urlStr 图片的存放路径
         */
        public static String putImages(InputStream inputStream, String imagesName, MultipartFile multipartFile){
            try {
                ChannelSftp sftp = getChannel();
                String path = rootPath + "/";
                createDir(path,sftp);
                //上传文件
                sftp.put(inputStream, path + imagesName);
                sftp.quit();
                sftp.exit();
                //处理返回的路径
                String resultFile;
                resultFile = imgUrl + imagesName;

                return resultFile;

            } catch (Exception e) {
                System.out.println("上传失败");
            }
            return "";
        }

        /**
         * 创建目录
         */
        private static void createDir(String path,ChannelSftp sftp) throws SftpException {
            String[] folders = path.split("/");
            sftp.cd("/");
            for ( String folder : folders ) {
                if ( folder.length() > 0 ) {
                    try {
                        sftp.cd( folder );
                    }catch ( SftpException e ) {
                        sftp.mkdir( folder );
                        sftp.cd( folder );
                    }
                }
            }
        }



}
