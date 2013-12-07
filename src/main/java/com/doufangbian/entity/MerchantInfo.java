package com.doufangbian.entity;

/**
 * 商家信息
 *
 * @author C_C
 */
public class MerchantInfo {

    private int id;// 商家Id
    private String name;// 商家名称
    private String major;// 搜索关键字
    private String introduction;// 商家简介
    private String news;// 商家最新资讯
    private String address;// 商家地址
    private String phone;// 商家电话
    private String isrecommend;// 是否推荐
    private String status;// 是否付费
    private int up;// 顶条数
    private int countyID;// 县id
    private int state; //0禁用1启用

    private String time;//创建时间

    private String updatetime;//更新时间

    private String last_reply;//最后回复时间

    private String log;//商家log

    private int share;//分享条数

    private int seecount;//浏览量

    private int updatecount;//最后回复更新条数

    private int commentcount;//商家评论条数

    private int groupId; // 商家所属的组号

    public int getId() {
        return id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getSeecount() {
        return seecount;
    }

    public void setSeecount(int seecount) {
        this.seecount = seecount;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsrecommend() {
        return isrecommend;
    }

    public void setIsrecommend(String isrecommend) {
        this.isrecommend = isrecommend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getCountyID() {
        return countyID;
    }

    public void setCountyID(int countyID) {
        this.countyID = countyID;
    }

    public void setLast_reply(String last_reply) {
        this.last_reply = last_reply;
    }

    public String getLast_reply() {
        return last_reply;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatecount(int updatecount) {
        this.updatecount = updatecount;
    }

    public int getUpdatecount() {
        return updatecount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
