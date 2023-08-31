package com.atguigu.qqzone.controller;

import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.UserBasicService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserController {

    private UserBasicService userBasicService;
    //登录控制
    public String login(String loginId, String pwd, HttpSession session) {
        UserBasic userBasic = userBasicService.login(loginId, pwd);
        if (userBasic!= null) {
            //1-1 获取相关的好友信息
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
//            //1-2 获取相关的日志列表信息(但是，日志只有id，没有其他信息）
//            List<Topic> topicList = new TopicDAOImpl().getTopicList(userBasic);
            userBasic.setFriendList(friendList);
//            userBasic.setTopicList(topicList);
            session.setAttribute("userBasic", userBasic);
            return "index";
        }
        return "login";
    }
}
