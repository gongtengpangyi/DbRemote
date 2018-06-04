package com.example.madb.client.controller;

import com.example.madb.model.pojo.User;
import com.example.madb.model.service.UserService;
import com.frz.frame.helper.JSONUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping("/test1")
    @ResponseBody
    public String test1() {
        User user = userService.findById(1);
        JSONObject userJson = JSONUtil.fromObject(user, "*");
        return userJson.toString();
    }

}
