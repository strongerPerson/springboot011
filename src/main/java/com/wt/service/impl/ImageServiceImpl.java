package com.wt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wt.mapper.ImageMapper;
import com.wt.pojo.Image;
import com.wt.service.ImageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wutian
 * @since 2021-10-17
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

}
