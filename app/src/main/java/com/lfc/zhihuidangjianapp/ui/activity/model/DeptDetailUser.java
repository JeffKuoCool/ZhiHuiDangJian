package com.lfc.zhihuidangjianapp.ui.activity.model;

/**
 * @date: 2019-08-03
 * @autror: guojian
 * @description:
 */
public class DeptDetailUser {

    private String displayName;
    private String subordinatePartyGroup;
    private String mobileNumber;
    private String partyPosts;

    public String getPartyPosts() {
        return partyPosts;
    }

    public void setPartyPosts(String partyPosts) {
        this.partyPosts = partyPosts;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSubordinatePartyGroup() {
        return subordinatePartyGroup;
    }

    public void setSubordinatePartyGroup(String subordinatePartyGroup) {
        this.subordinatePartyGroup = subordinatePartyGroup;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
