package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicDAO {
    //获取日志列表
    public List<Topic> getTopicList(UserBasic userBasic);

}
