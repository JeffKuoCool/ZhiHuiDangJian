package com.lfc.zhihuidangjianapp.ui.activity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @date: 2019-08-07
 * @autror: guojian
 * @description:
 */
public class User implements Parcelable {

    private String addr;
    private String addr1;
    private String addr2;
    private String addr3;
    private String addr4;
    private String avatar;
    private String azRole;
    private String beginCreateDt;
    private long birthday;
    private String company;
    private long createDt;
    private String createrId;
    private String createrIp;
    private String createrName;
    private String deptId;
    private String deptName;
    private String deptNumber;
    private String displayName;
    private String education;
    private String email;
    private int emailVerify;
    private int enabled;
    private String endCreateDt;
    private String graduateSchool;
    private String groupByClause;
    private String homeAddress;
    private String ifPartyMember;
    private String imgAddress;
    private int isAdmin;
    private String isAz;
    private int isDelete;
    private String isDeleteText;
    private String isEnableText;
    private String jobTitle;
    private String joinPartyIntroducer;
    private String joinPartyTime;
    private String joinWorkTime;
    private String loginName;
    private String loginPwd;
    private String majorStudied;
    private String mobile;
    private String mobileNumber;
    private int mobileVerify;
    private String modifierId;
    private String modifierName;
    private String modifyDt;
    //民族
    private String nation;
    private String nativePlace;
    private String number;
    private String orderByClause;
    private int pageNumber;
    private int pageSize;
    private String partyPosts;
    private int personStatus;
    private String phone;
    private String pinyin;
    private String postId;
    private String postKey;
    private String postValue;
    private String qq;
    private String remark;
    private String retireTime;
    private String roeId;
    private String roleName;
    private String sealName;
    private int sex;
    private String starPartyMember;
    private int startIndex;
    private int status;
    private String subordinatePartyGroup;
    private String toFormalPartyTime;
    private String token;
    private String userId;
    private String userNumber;
    private String userType;
    private String workPost;
    private String imPwd = "zhdj-888888";

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roleName);
        dest.writeString(imgAddress);
        dest.writeString(loginName);
        dest.writeString(imPwd);
        dest.writeString(mobileNumber);
        dest.writeString(deptName);
        dest.writeString(sealName);
    }

    public User() {
    }

    protected User(Parcel in) {
        roleName = in.readString();
        imgAddress = in.readString();
        loginName = in.readString();
        imPwd = in.readString();
        mobileNumber = in.readString();
        deptName = in.readString();
        sealName = in.readString();
    }


    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getAddr3() {
        return addr3;
    }

    public void setAddr4(String addr4) {
        this.addr4 = addr4;
    }

    public String getAddr4() {
        return addr4;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAzRole(String azRole) {
        this.azRole = azRole;
    }

    public String getAzRole() {
        return azRole;
    }

    public void setBeginCreateDt(String beginCreateDt) {
        this.beginCreateDt = beginCreateDt;
    }

    public String getBeginCreateDt() {
        return beginCreateDt;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCreateDt(long createDt) {
        this.createDt = createDt;
    }

    public long getCreateDt() {
        return createDt;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterIp(String createrIp) {
        this.createrIp = createrIp;
    }

    public String getCreaterIp() {
        return createrIp;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptNumber(String deptNumber) {
        this.deptNumber = deptNumber;
    }

    public String getDeptNumber() {
        return deptNumber;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmailVerify(int emailVerify) {
        this.emailVerify = emailVerify;
    }

    public int getEmailVerify() {
        return emailVerify;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEndCreateDt(String endCreateDt) {
        this.endCreateDt = endCreateDt;
    }

    public String getEndCreateDt() {
        return endCreateDt;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
    }

    public String getGroupByClause() {
        return groupByClause;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setIfPartyMember(String ifPartyMember) {
        this.ifPartyMember = ifPartyMember;
    }

    public String getIfPartyMember() {
        return ifPartyMember;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAz(String isAz) {
        this.isAz = isAz;
    }

    public String getIsAz() {
        return isAz;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsDelete() {
        return isDelete;
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

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJoinPartyIntroducer(String joinPartyIntroducer) {
        this.joinPartyIntroducer = joinPartyIntroducer;
    }

    public String getJoinPartyIntroducer() {
        return joinPartyIntroducer;
    }

    public void setJoinPartyTime(String joinPartyTime) {
        this.joinPartyTime = joinPartyTime;
    }

    public String getJoinPartyTime() {
        return joinPartyTime;
    }

    public void setJoinWorkTime(String joinWorkTime) {
        this.joinWorkTime = joinWorkTime;
    }

    public String getJoinWorkTime() {
        return joinWorkTime;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setMajorStudied(String majorStudied) {
        this.majorStudied = majorStudied;
    }

    public String getMajorStudied() {
        return majorStudied;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileVerify(int mobileVerify) {
        this.mobileVerify = mobileVerify;
    }

    public int getMobileVerify() {
        return mobileVerify;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNation() {
        return nation;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
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

    public void setPersonStatus(int personStatus) {
        this.personStatus = personStatus;
    }

    public int getPersonStatus() {
        return personStatus;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostValue(String postValue) {
        this.postValue = postValue;
    }

    public String getPostValue() {
        return postValue;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQq() {
        return qq;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRetireTime(String retireTime) {
        this.retireTime = retireTime;
    }

    public String getRetireTime() {
        return retireTime;
    }

    public void setRoeId(String roeId) {
        this.roeId = roeId;
    }

    public String getRoeId() {
        return roeId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setStarPartyMember(String starPartyMember) {
        this.starPartyMember = starPartyMember;
    }

    public String getStarPartyMember() {
        return starPartyMember;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setSubordinatePartyGroup(String subordinatePartyGroup) {
        this.subordinatePartyGroup = subordinatePartyGroup;
    }

    public String getSubordinatePartyGroup() {
        return subordinatePartyGroup;
    }

    public void setToFormalPartyTime(String toFormalPartyTime) {
        this.toFormalPartyTime = toFormalPartyTime;
    }

    public String getToFormalPartyTime() {
        return toFormalPartyTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    public String getWorkPost() {
        return workPost;
    }

    public String getImPwd() {
        return imPwd == null ? "zhdj-888888" : imPwd;
    }

    public void setImPwd(String imPwd) {
        this.imPwd = imPwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
