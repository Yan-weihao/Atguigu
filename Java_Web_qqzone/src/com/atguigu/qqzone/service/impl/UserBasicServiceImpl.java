package com.atguigu.qqzone.service.impl;

import com.atguigu.qqzone.dao.impl.UserBasicDAOImpl;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.UserBasicService;

import java.util.List;

/**
 * 实现UserBasicService接口
 */
public class UserBasicServiceImpl implements UserBasicService {
    UserBasicDAOImpl userBasicDAO = null;


    @Override
    public UserBasic login(String loginId, String password) {
        return userBasicDAO.getUserBasic(loginId, password);
    }
    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        return userBasicDAO.getFriendList(userBasic);
    }

    @Override
    public void setUserBasicList(Topic topic) {

    }
}
