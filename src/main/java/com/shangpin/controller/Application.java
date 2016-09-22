package com.shangpin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wenyu on 2016/9/21.
 */
@Controller
@RequestMapping("/app")
public class Application {
    @RequestMapping("/methodPath")
    public String method() {
        return "mapping url is /classPath/methodPath";
    }
    @RequestMapping("/users/{username}")
    public String userProfile(@PathVariable("username") String username , Model model) {
        //return String.format("user %s", username);
        model.addAttribute("name",username);
        return "hello";
    }

//    @RequestMapping("/posts/{id}")
//    public String post(@PathVariable("id") int id) {
//        return String.format("post %d", id);
//    }
}
