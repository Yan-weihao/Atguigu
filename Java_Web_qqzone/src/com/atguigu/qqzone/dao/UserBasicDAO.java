package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicDAO {
    //查询用户登录信息
    public UserBasic getUserBasic(String loginId , String password);
    //根据用户id查询用户
    public UserBasic getUserBasicById(String Id);
    //用户列表查询
    public List<UserBasic> getUserBasicList(UserBasic userBasic);

}
