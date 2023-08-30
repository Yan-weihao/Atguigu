package com.atguigu.qqzone.dao.Impl;

import com.atguigu.myssm.baseDao.base.BaseDAO;
import com.atguigu.qqzone.dao.UserBasicDAO;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

public class UserBasicDAOImpl extends BaseDAO<UserBasic> implements UserBasicDAO {


    @Override
    public UserBasic getUserBasic(String loginId, String password) {
        return super.load("select * from t_user_basic where login_id=? and password=?", loginId, password);
    }

    @Override
    public UserBasic getUserBasicById(String Id) {
        return super.load("select * from t_user_basic where id=?", Id);
    }

    @Override
    public List<UserBasic> getUserBasicList(UserBasic userBasic) {
        return super.executeQuery("SELECT fid as 'id' FROM t_friend WHERE uid = ?", userBasic);
    }

}
