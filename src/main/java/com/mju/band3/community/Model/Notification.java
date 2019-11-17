package com.mju.band3.community.Model;

public class Notification {
    private Long id;

    private Long notifier;

    private Long receiver;

    private Integer outerid;

    private Integer type;

    private Long gmtCreate;

    private Integer status;

    private String notifierName;

    private String outerTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotifier() {
        return notifier;
    }

    public void setNotifier(Long notifier) {
        this.notifier = notifier;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Integer getOuterid() {
        return outerid;
    }

    public void setOuterid(Integer outerid) {
        this.outerid = outerid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotifierName() {
        return notifierName;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName == null ? null : notifierName.trim();
    }

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {

        this.outerTitle = outerTitle == null ? null : outerTitle.trim();
    }


    public Notification() {
    }

    public Notification(Long id, Integer status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", notifier=" + notifier +
                ", receiver=" + receiver +
                ", outerid=" + outerid +
                ", type=" + type +
                ", gmtCreate=" + gmtCreate +
                ", status=" + status +
                ", notifierName='" + notifierName + '\'' +
                ", outerTitle='" + outerTitle + '\'' +
                '}';
    }
}