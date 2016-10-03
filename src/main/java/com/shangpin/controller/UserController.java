package com.shangpin.controller;

import com.shangpin.pojo.User;
import com.shangpin.service.UserService;
import com.shangpin.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wenyu on 2016/9/22.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/list")
    public List<User> getStus(){
        logger.info("从数据库读取Student集合");
       // return userService.getList();
        return userMapper.getList();
    }
}
