package com.atguigu.qqzone.service.Impl;

import com.atguigu.qqzone.dao.Impl.UserBasicDAOImpl;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.UserBasicService;

import java.util.List;

/**
 * 实现UserBasicService接口
 */
public class UserBasicServiceImpl implements UserBasicService {
    UserBasicDAOImpl userBasicdaoImpl = null;


    @Override
    public UserBasic login(String loginId, String password) {
        return userBasicdaoImpl.getUserBasic(loginId, password);
    }
    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        return null;
    }
}
