package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicService {
    //登录
    UserBasic login(String loginId, String password);
    //获取好友列表
    List<UserBasic> getFriendList(UserBasic userBasic);
}
