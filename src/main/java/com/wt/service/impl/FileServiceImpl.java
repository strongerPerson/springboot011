package com.wt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wt.mapper.FileMapper;
import com.wt.pojo.File;
import com.wt.service.FileService;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper , File> implements FileService{
}
