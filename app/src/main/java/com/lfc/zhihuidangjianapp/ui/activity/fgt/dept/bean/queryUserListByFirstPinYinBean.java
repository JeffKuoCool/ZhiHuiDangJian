package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean;

import java.util.List;

public class queryUserListByFirstPinYinBean {

    /**
     * code : 0
     * data : {"userList":[{"addr":null,"addr1":null,"addr2":null,"addr3":null,"addr4":null,"avatar":null,"azRole":null,"beginCreateDt":null,"birthday":264787200000,"company":null,"createDt":1559117065000,"createrId":"admin","createrIp":null,"createrName":"admin","deptId":null,"deptName":"中共山西涑水河源头省级自然保护区管理局支部委员会","deptNumber":"10011","displayName":"陈达","education":"大专","email":null,"emailVerify":-1,"enabled":-1,"endCreateDt":null,"graduateSchool":"山西农业大学","groupByClause":null,"homeAddress":null,"ifPartyMember":null,"imgAddress":null,"isAdmin":-1,"isAz":null,"isDelete":1,"isDeleteText":null,"isEnableText":null,"jobTitle":null,"joinPartyIntroducer":"樊保仲、杨于军","joinPartyTime":1560239290000,"joinWorkTime":"1997.01","loginName":"1005","loginPwd":"6F0ED126FF2C2B3E82511078E2FF3F82","majorStudied":"林业技术","mobile":null,"mobileNumber":"15035932897","mobileVerify":-1,"modifierId":null,"modifierName":null,"modifyDt":null,"nation":"汉族","nativePlace":null,"number":"014000073264","orderByClause":null,"pageNumber":1,"pageSize":10,"partyPosts":null,"personStatus":3,"phone":null,"pinyin":"ChenDa","postId":"","postKey":null,"postValue":null,"qq":null,"remark":null,"retireTime":"2019-06-03 08:28:02","roeId":null,"roleName":null,"sealName":"陈达","sex":1,"starPartyMember":null,"startIndex":0,"status":0,"subordinatePartyGroup":"第一党小组","toFormalPartyTime":1356796800000,"token":null,"userId":"4fe1ad46328f46e69258fa6869f7cd7b","userNumber":"1005","userType":null,"workPost":null}]}
     * msg : 请求成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<UserListBean> userList;

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean {
            /**
             * addr : null
             * addr1 : null
             * addr2 : null
             * addr3 : null
             * addr4 : null
             * avatar : null
             * azRole : null
             * beginCreateDt : null
             * birthday : 264787200000
             * company : null
             * createDt : 1559117065000
             * createrId : admin
             * createrIp : null
             * createrName : admin
             * deptId : null
             * deptName : 中共山西涑水河源头省级自然保护区管理局支部委员会
             * deptNumber : 10011
             * displayName : 陈达
             * education : 大专
             * email : null
             * emailVerify : -1
             * enabled : -1
             * endCreateDt : null
             * graduateSchool : 山西农业大学
             * groupByClause : null
             * homeAddress : null
             * ifPartyMember : null
             * imgAddress : null
             * isAdmin : -1
             * isAz : null
             * isDelete : 1
             * isDeleteText : null
             * isEnableText : null
             * jobTitle : null
             * joinPartyIntroducer : 樊保仲、杨于军
             * joinPartyTime : 1560239290000
             * joinWorkTime : 1997.01
             * loginName : 1005
             * loginPwd : 6F0ED126FF2C2B3E82511078E2FF3F82
             * majorStudied : 林业技术
             * mobile : null
             * mobileNumber : 15035932897
             * mobileVerify : -1
             * modifierId : null
             * modifierName : null
             * modifyDt : null
             * nation : 汉族
             * nativePlace : null
             * number : 014000073264
             * orderByClause : null
             * pageNumber : 1
             * pageSize : 10
             * partyPosts : null
             * personStatus : 3
             * phone : null
             * pinyin : ChenDa
             * postId :
             * postKey : null
             * postValue : null
             * qq : null
             * remark : null
             * retireTime : 2019-06-03 08:28:02
             * roeId : null
             * roleName : null
             * sealName : 陈达
             * sex : 1
             * starPartyMember : null
             * startIndex : 0
             * status : 0
             * subordinatePartyGroup : 第一党小组
             * toFormalPartyTime : 1356796800000
             * token : null
             * userId : 4fe1ad46328f46e69258fa6869f7cd7b
             * userNumber : 1005
             * userType : null
             * workPost : null
             */

            private Object addr;
            private Object addr1;
            private Object addr2;
            private Object addr3;
            private Object addr4;
            private Object avatar;
            private Object azRole;
            private Object beginCreateDt;
            private long birthday;
            private Object company;
            private long createDt;
            private String createrId;
            private Object createrIp;
            private String createrName;
            private Object deptId;
            private String deptName;
            private String deptNumber;
            private String displayName;
            private String education;
            private Object email;
            private int emailVerify;
            private int enabled;
            private Object endCreateDt;
            private String graduateSchool;
            private Object groupByClause;
            private Object homeAddress;
            private Object ifPartyMember;
            private Object imgAddress;
            private int isAdmin;
            private Object isAz;
            private int isDelete;
            private Object isDeleteText;
            private Object isEnableText;
            private Object jobTitle;
            private String joinPartyIntroducer;
            private String joinPartyTime;
            private String joinWorkTime;
            private String loginName;
            private String loginPwd;
            private String majorStudied;
            private Object mobile;
            private String mobileNumber;
            private int mobileVerify;
            private Object modifierId;
            private Object modifierName;
            private Object modifyDt;
            private String nation;
            private Object nativePlace;
            private String number;
            private Object orderByClause;
            private int pageNumber;
            private int pageSize;
            private Object partyPosts;
            private int personStatus;
            private Object phone;
            private String pinyin;
            private String postId;
            private Object postKey;
            private Object postValue;
            private Object qq;
            private Object remark;
            private String retireTime;
            private Object roeId;
            private Object roleName;
            private String sealName;
            private int sex;
            private Object starPartyMember;
            private int startIndex;
            private int status;
            private String subordinatePartyGroup;
            private String toFormalPartyTime;
            private Object token;
            private String userId;
            private String userNumber;
            private Object userType;
            private Object workPost;

            public Object getAddr() {
                return addr;
            }

            public void setAddr(Object addr) {
                this.addr = addr;
            }

            public Object getAddr1() {
                return addr1;
            }

            public void setAddr1(Object addr1) {
                this.addr1 = addr1;
            }

            public Object getAddr2() {
                return addr2;
            }

            public void setAddr2(Object addr2) {
                this.addr2 = addr2;
            }

            public Object getAddr3() {
                return addr3;
            }

            public void setAddr3(Object addr3) {
                this.addr3 = addr3;
            }

            public Object getAddr4() {
                return addr4;
            }

            public void setAddr4(Object addr4) {
                this.addr4 = addr4;
            }

            public Object getAvatar() {
                return avatar;
            }

            public void setAvatar(Object avatar) {
                this.avatar = avatar;
            }

            public Object getAzRole() {
                return azRole;
            }

            public void setAzRole(Object azRole) {
                this.azRole = azRole;
            }

            public Object getBeginCreateDt() {
                return beginCreateDt;
            }

            public void setBeginCreateDt(Object beginCreateDt) {
                this.beginCreateDt = beginCreateDt;
            }

            public long getBirthday() {
                return birthday;
            }

            public void setBirthday(long birthday) {
                this.birthday = birthday;
            }

            public Object getCompany() {
                return company;
            }

            public void setCompany(Object company) {
                this.company = company;
            }

            public long getCreateDt() {
                return createDt;
            }

            public void setCreateDt(long createDt) {
                this.createDt = createDt;
            }

            public String getCreaterId() {
                return createrId;
            }

            public void setCreaterId(String createrId) {
                this.createrId = createrId;
            }

            public Object getCreaterIp() {
                return createrIp;
            }

            public void setCreaterIp(Object createrIp) {
                this.createrIp = createrIp;
            }

            public String getCreaterName() {
                return createrName;
            }

            public void setCreaterName(String createrName) {
                this.createrName = createrName;
            }

            public Object getDeptId() {
                return deptId;
            }

            public void setDeptId(Object deptId) {
                this.deptId = deptId;
            }

            public String getDeptName() {
                return deptName;
            }

            public void setDeptName(String deptName) {
                this.deptName = deptName;
            }

            public String getDeptNumber() {
                return deptNumber;
            }

            public void setDeptNumber(String deptNumber) {
                this.deptNumber = deptNumber;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public String getEducation() {
                return education;
            }

            public void setEducation(String education) {
                this.education = education;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public int getEmailVerify() {
                return emailVerify;
            }

            public void setEmailVerify(int emailVerify) {
                this.emailVerify = emailVerify;
            }

            public int getEnabled() {
                return enabled;
            }

            public void setEnabled(int enabled) {
                this.enabled = enabled;
            }

            public Object getEndCreateDt() {
                return endCreateDt;
            }

            public void setEndCreateDt(Object endCreateDt) {
                this.endCreateDt = endCreateDt;
            }

            public String getGraduateSchool() {
                return graduateSchool;
            }

            public void setGraduateSchool(String graduateSchool) {
                this.graduateSchool = graduateSchool;
            }

            public Object getGroupByClause() {
                return groupByClause;
            }

            public void setGroupByClause(Object groupByClause) {
                this.groupByClause = groupByClause;
            }

            public Object getHomeAddress() {
                return homeAddress;
            }

            public void setHomeAddress(Object homeAddress) {
                this.homeAddress = homeAddress;
            }

            public Object getIfPartyMember() {
                return ifPartyMember;
            }

            public void setIfPartyMember(Object ifPartyMember) {
                this.ifPartyMember = ifPartyMember;
            }

            public Object getImgAddress() {
                return imgAddress;
            }

            public void setImgAddress(Object imgAddress) {
                this.imgAddress = imgAddress;
            }

            public int getIsAdmin() {
                return isAdmin;
            }

            public void setIsAdmin(int isAdmin) {
                this.isAdmin = isAdmin;
            }

            public Object getIsAz() {
                return isAz;
            }

            public void setIsAz(Object isAz) {
                this.isAz = isAz;
            }

            public int getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(int isDelete) {
                this.isDelete = isDelete;
            }

            public Object getIsDeleteText() {
                return isDeleteText;
            }

            public void setIsDeleteText(Object isDeleteText) {
                this.isDeleteText = isDeleteText;
            }

            public Object getIsEnableText() {
                return isEnableText;
            }

            public void setIsEnableText(Object isEnableText) {
                this.isEnableText = isEnableText;
            }

            public Object getJobTitle() {
                return jobTitle;
            }

            public void setJobTitle(Object jobTitle) {
                this.jobTitle = jobTitle;
            }

            public String getJoinPartyIntroducer() {
                return joinPartyIntroducer;
            }

            public void setJoinPartyIntroducer(String joinPartyIntroducer) {
                this.joinPartyIntroducer = joinPartyIntroducer;
            }

            public String getJoinPartyTime() {
                return joinPartyTime;
            }

            public void setJoinPartyTime(String joinPartyTime) {
                this.joinPartyTime = joinPartyTime;
            }

            public String getJoinWorkTime() {
                return joinWorkTime;
            }

            public void setJoinWorkTime(String joinWorkTime) {
                this.joinWorkTime = joinWorkTime;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getLoginPwd() {
                return loginPwd;
            }

            public void setLoginPwd(String loginPwd) {
                this.loginPwd = loginPwd;
            }

            public String getMajorStudied() {
                return majorStudied;
            }

            public void setMajorStudied(String majorStudied) {
                this.majorStudied = majorStudied;
            }

            public Object getMobile() {
                return mobile;
            }

            public void setMobile(Object mobile) {
                this.mobile = mobile;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public int getMobileVerify() {
                return mobileVerify;
            }

            public void setMobileVerify(int mobileVerify) {
                this.mobileVerify = mobileVerify;
            }

            public Object getModifierId() {
                return modifierId;
            }

            public void setModifierId(Object modifierId) {
                this.modifierId = modifierId;
            }

            public Object getModifierName() {
                return modifierName;
            }

            public void setModifierName(Object modifierName) {
                this.modifierName = modifierName;
            }

            public Object getModifyDt() {
                return modifyDt;
            }

            public void setModifyDt(Object modifyDt) {
                this.modifyDt = modifyDt;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public Object getNativePlace() {
                return nativePlace;
            }

            public void setNativePlace(Object nativePlace) {
                this.nativePlace = nativePlace;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public Object getOrderByClause() {
                return orderByClause;
            }

            public void setOrderByClause(Object orderByClause) {
                this.orderByClause = orderByClause;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public Object getPartyPosts() {
                return partyPosts;
            }

            public void setPartyPosts(Object partyPosts) {
                this.partyPosts = partyPosts;
            }

            public int getPersonStatus() {
                return personStatus;
            }

            public void setPersonStatus(int personStatus) {
                this.personStatus = personStatus;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public String getPinyin() {
                return pinyin;
            }

            public void setPinyin(String pinyin) {
                this.pinyin = pinyin;
            }

            public String getPostId() {
                return postId;
            }

            public void setPostId(String postId) {
                this.postId = postId;
            }

            public Object getPostKey() {
                return postKey;
            }

            public void setPostKey(Object postKey) {
                this.postKey = postKey;
            }

            public Object getPostValue() {
                return postValue;
            }

            public void setPostValue(Object postValue) {
                this.postValue = postValue;
            }

            public Object getQq() {
                return qq;
            }

            public void setQq(Object qq) {
                this.qq = qq;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getRetireTime() {
                return retireTime;
            }

            public void setRetireTime(String retireTime) {
                this.retireTime = retireTime;
            }

            public Object getRoeId() {
                return roeId;
            }

            public void setRoeId(Object roeId) {
                this.roeId = roeId;
            }

            public Object getRoleName() {
                return roleName;
            }

            public void setRoleName(Object roleName) {
                this.roleName = roleName;
            }

            public String getSealName() {
                return sealName;
            }

            public void setSealName(String sealName) {
                this.sealName = sealName;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public Object getStarPartyMember() {
                return starPartyMember;
            }

            public void setStarPartyMember(Object starPartyMember) {
                this.starPartyMember = starPartyMember;
            }

            public int getStartIndex() {
                return startIndex;
            }

            public void setStartIndex(int startIndex) {
                this.startIndex = startIndex;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSubordinatePartyGroup() {
                return subordinatePartyGroup;
            }

            public void setSubordinatePartyGroup(String subordinatePartyGroup) {
                this.subordinatePartyGroup = subordinatePartyGroup;
            }

            public String getToFormalPartyTime() {
                return toFormalPartyTime;
            }

            public void setToFormalPartyTime(String toFormalPartyTime) {
                this.toFormalPartyTime = toFormalPartyTime;
            }

            public Object getToken() {
                return token;
            }

            public void setToken(Object token) {
                this.token = token;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserNumber() {
                return userNumber;
            }

            public void setUserNumber(String userNumber) {
                this.userNumber = userNumber;
            }

            public Object getUserType() {
                return userType;
            }

            public void setUserType(Object userType) {
                this.userType = userType;
            }

            public Object getWorkPost() {
                return workPost;
            }

            public void setWorkPost(Object workPost) {
                this.workPost = workPost;
            }
        }
    }
}
