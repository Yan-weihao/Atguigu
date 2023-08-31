package com.atguigu.qqzone.dao.impl;

import com.atguigu.myssm.baseDao.base.BaseDAO;
import com.atguigu.qqzone.dao.UserBasicDAO;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

public class UserBasicDAOImpl extends BaseDAO<UserBasic> implements UserBasicDAO {


    @Override
    public UserBasic getUserBasic(String loginId, String password) {
        return super.load("select * from t_user_basic where loginId = ? and pwd = ? ", loginId, password);
    }

    @Override
    public UserBasic getUserBasicById(String Id) {
        return super.load("select * from t_user_basic where id=?", Id);
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        String sql = "select t3.id from t_user_basic t1 join t_friend t2 on t1.id=t2.uid join t_user_basic t3 on t2.fid = t3.id where t1.id = ?";
        return super.executeQuery(sql, userBasic.getId());
    }
}
