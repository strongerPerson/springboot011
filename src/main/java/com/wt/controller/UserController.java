package com.wt.controller;


import com.wt.pojo.User;
import com.wt.service.UserService;
import com.wt.utils.ResultCode;
import com.wt.utils.ResultCommon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping("/user")
@EnableSwagger2
@Api("用户管理")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 1，查询用户信息(所有用户）
     */
    @GetMapping("/list")
    @ApiOperation("查询所有用户信息")
    public ResultCommon queryUserLists(){
        List<User> list = userService.list();
        if(list!=null){
          return ResultCommon.success(ResultCode.SUCCESS,list);
        }else{
            return ResultCommon.fail(ResultCode.FAIL);
        }
    }
    /**
     * 2,通过id查询用户信息
     */

    @ApiOperation("通过id查询用户信息")
    @ApiImplicitParam(name = "id",value = "用户id",required = false,dataType = "Integer")
    @GetMapping("{id}")
    public ResultCommon queryById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        if(user!=null){
            return ResultCommon.success(ResultCode.SUCCESS,user);
        }else{
            return ResultCommon.fail(ResultCode.FAIL);
        }

    }
    /**
     * 3，通过id删除用户信息
     */
    @ApiOperation("通过用户id删除用户信息")
    @ApiImplicitParam(name = "id",value = "用户id",required = false,dataType = "Integer")
    @DeleteMapping("{id}")
    public ResultCommon deleteUser(@PathVariable("id") Integer id){

        boolean b = userService.removeById(id);
        if(b){
            return ResultCommon.success(ResultCode.SUCCESS);
        }else{
            return ResultCommon.fail(ResultCode.FAIL);
        }

    }
    /**
     * 4，通过用户id修改数据
     */
    @ApiOperation("通过id修改用户信息")
    @ApiImplicitParam(name = "user",value = "用户对象",paramType = "path",required = false,dataType = "User")
    @PutMapping("/updateUser")
    public ResultCommon updateUser( User user){
        Integer id = user.getId();
        boolean b = userService.updateById(user);
        if(b){
            return ResultCommon.success(ResultCode.SUCCESS);
        }else{
            return ResultCommon.fail(ResultCode.FAIL);
        }


    }
    /**
     * 5，增加用户信息
     */
    @ApiOperation("增加用户信息")
    @ApiImplicitParam(name = "user",value = "用户对象",paramType = "path",required = false,dataType = "User")
    @PostMapping("/addUser")
    public  ResultCommon addUser(User user){
        boolean save = userService.save(user);
        System.out.println(user+"0000000");
        if(save){
            return ResultCommon.success(ResultCode.SUCCESS);
        }else{
            return ResultCommon.fail(ResultCode.FAIL);
        }
    }

}
