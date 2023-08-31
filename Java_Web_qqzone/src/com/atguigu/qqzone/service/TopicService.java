package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicService {
    //获取用户的日志列表
    public List<Topic> getTopicList(UserBasic userBasic);
    //添加日志
    public  void addTopic(Topic topic);

}

