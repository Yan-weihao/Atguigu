package com.atguigu.qqzone.pojo;

import java.util.Date;
import java.util.List;

/**
 * 日志信息
 */
public class Topic {
    private Integer id;
   private String title;
   private String content;
   private Date topicDate;
   private UserBasic author;//M:1
   private List<Reply> comments; //1:M

    public Topic() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(Date topicDate) {
        this.topicDate = topicDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public List<Reply> getComments() {
        return comments;
    }

    public void setComments(List<Reply> comments) {
        this.comments = comments;
    }
}
