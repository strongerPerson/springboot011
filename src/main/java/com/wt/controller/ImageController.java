package com.wt.controller;


import com.wt.pojo.Image;
import com.wt.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wutian
 * @since 2021-10-17
 */
@Controller
@RequestMapping("server/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    /**
     * 通过id查询数据
     */

    @GetMapping("{id}")
    public String queryById(@PathVariable("id") Integer id,Model mav){

        //获取到查询的数据
        Image byId = imageService.getById(id);
        String src = byId.getSrc();
        // //将数据放置到ModelAndView对象view中,第二个参数可以是任何java类型
        mav.addAttribute("message", src);
        return"message";
    }


    }






