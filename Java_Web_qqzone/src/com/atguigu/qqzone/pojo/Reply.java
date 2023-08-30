package com.atguigu.qqzone.pojo;

import java.util.Date;

/**
 * 回复信息
 */
public class Reply {
    private Integer replyId;
    private String Content;
    private Date ReplyTime;
    private UserBasic author;
    private Topic topic;
    private  HostReply hostReply;

    public Reply() {
    }
    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getReplyTime() {
        return ReplyTime;
    }

    public void setReplyTime(Date replyTime) {
        ReplyTime = replyTime;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public HostReply getHostReply() {
        return hostReply;
    }

    public void setHostReply(HostReply hostReply) {
        this.hostReply = hostReply;
    }


}
