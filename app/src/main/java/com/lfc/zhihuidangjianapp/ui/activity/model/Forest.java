package com.lfc.zhihuidangjianapp.ui.activity.model;

import java.io.Serializable;

/**
 * @date: 2019-08-09
 * @autror: guojian
 * @description:
 */
public class Forest implements Serializable {

    private String author;
    private String beginCreateDt;
    private String birthday;
    private String deptName;
    private String deptNumber;
    private String education;
    private String endCreateDt;
    private int forestDistrictId;
    private int forestDistrictType;
    private String groupByClause;
    private String isDeleteText;
    private String isEnableText;
    private String orderByClause;
    private int pageNumber;
    private int pageSize;
    private String partyPosts;
    private int startIndex;
    private String thumbnailAppUrl;
    private String thumbnailSmallUrl;
    private String thumbnailUrl;
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setBeginCreateDt(String beginCreateDt) {
        this.beginCreateDt = beginCreateDt;
    }

    public String getBeginCreateDt() {
        return beginCreateDt;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday==null?"暂无":birthday;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName==null?"暂无":deptName;
    }

    public void setDeptNumber(String deptNumber) {
        this.deptNumber = deptNumber;
    }

    public String getDeptNumber() {
        return deptNumber;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education==null?"暂无":education;
    }

    public void setEndCreateDt(String endCreateDt) {
        this.endCreateDt = endCreateDt;
    }

    public String getEndCreateDt() {
        return endCreateDt;
    }

    public void setForestDistrictId(int forestDistrictId) {
        this.forestDistrictId = forestDistrictId;
    }

    public int getForestDistrictId() {
        return forestDistrictId;
    }

    public void setForestDistrictType(int forestDistrictType) {
        this.forestDistrictType = forestDistrictType;
    }

    public int getForestDistrictType() {
        return forestDistrictType;
    }

    public void setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
    }

    public String getGroupByClause() {
        return groupByClause;
    }

    public void setIsDeleteText(String isDeleteText) {
        this.isDeleteText = isDeleteText;
    }

    public String getIsDeleteText() {
        return isDeleteText;
    }

    public void setIsEnableText(String isEnableText) {
        this.isEnableText = isEnableText;
    }

    public String getIsEnableText() {
        return isEnableText;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPartyPosts(String partyPosts) {
        this.partyPosts = partyPosts;
    }

    public String getPartyPosts() {
        return partyPosts;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setThumbnailAppUrl(String thumbnailAppUrl) {
        this.thumbnailAppUrl = thumbnailAppUrl;
    }

    public String getThumbnailAppUrl() {
        return thumbnailAppUrl;
    }

    public void setThumbnailSmallUrl(String thumbnailSmallUrl) {
        this.thumbnailSmallUrl = thumbnailSmallUrl;
    }

    public String getThumbnailSmallUrl() {
        return thumbnailSmallUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
