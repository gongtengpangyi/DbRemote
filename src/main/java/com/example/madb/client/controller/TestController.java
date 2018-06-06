package com.example.madb.client.controller;

import com.example.madb.console.remote.DBRemote;
import com.example.madb.console.remote.WaterObject;
import com.example.madb.model.pojo.User;
import com.example.madb.model.service.UserService;
import com.frz.frame.helper.JSONUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    UserService userService;
    @Autowired
    DBRemote dbRemote;

    @RequestMapping("/test1")
    @ResponseBody
    public String test1() {
//        changeSource();
        User user = userService.findById(1);
        JSONObject userJson = JSONUtil.fromObject(user, "*");
        return userJson.toString();
    }

    @RequestMapping("/addSource")
    @ResponseBody
    public String addSource(@RequestParam("objectId") Integer objectId, @RequestParam("dbName") String dbName) {
        WaterObject waterObject = new WaterObject();
        waterObject.setDbName(dbName);
        waterObject.setIp("localhost");
        waterObject.setPassword("123456");
        waterObject.setUser("root");
        waterObject.setPort(3306);
        waterObject.setObjectId(objectId);
        return String.valueOf(dbRemote.addWaterObject(waterObject));
    }

    @RequestMapping("/changeSource")
    @ResponseBody
    public String changeSource(@RequestParam("objectId") Integer objectId) {
//        addSource();
        return String.valueOf(dbRemote.changeWaterObject(objectId));
    }

}
