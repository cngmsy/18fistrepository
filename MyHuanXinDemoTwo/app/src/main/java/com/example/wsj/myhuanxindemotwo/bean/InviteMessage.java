package com.example.wsj.myhuanxindemotwo.bean;

/**
 * Created by user on 2016/7/6.
 */
public class InviteMessage {
    /**
     * 邀请消息发送者
     */
    private String fromname;
    /**
     * 邀请时间
     */
    private long time;
    /**
     *邀请理由
     */
    private String reason;
    /**
     *是否同意的一个状态
     */
    private boolean isAgree = false;
    /**
     *是否拒绝的状态
     */
    private boolean isRefuse = false;

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isAgree() {
        return isAgree;
    }

    public void setAgree(boolean agree) {
        isAgree = agree;
    }

    public boolean isRefuse() {
        return isRefuse;
    }

    public void setRefuse(boolean refuse) {
        isRefuse = refuse;
    }
}
