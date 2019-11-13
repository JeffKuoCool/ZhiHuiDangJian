package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean;

import java.util.List;

/**
 * Created by ${TXY}
 * Created on 2019/11/12
 * PackageName com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean
 * REMARK ${REMARK}
 **/
public class MonthBean {


    /**
     * monthWorkReportList : {"datas":[{"beginCreateDt":null,"beginTime":null,"collectiveLearningConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>3<\/p>\n<\/body>\n<\/html>","createCode":"1428","createName":"李晓娜","createTime":1573361667000,"dept":"中共山西省中条山国有林管理局侯马离退休支部委员会","deptCode":"10031","deptCommitteeConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>2<\/p>\n<\/body>\n<\/html>","deptNumberList":null,"deptPartyMeetingConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>1<\/p>\n<\/body>\n<\/html>","endCreateDt":null,"endTime":null,"groupByClause":null,"idList":null,"ifPass":0,"ifTop":1,"isDeleteText":null,"isEnableText":null,"monthWorkReportId":6,"orderByClause":null,"otherConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>5<\/p>\n<\/body>\n<\/html>","otherReportingMatters":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>6<\/p>\n<\/body>\n<\/html>","pageNumber":1,"pageSize":10,"remark":null,"startIndex":0,"themeDayConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>4<\/p>\n<\/body>\n<\/html>","title":"555","topNum":null,"urlList":null,"userName":"李晓娜","userNumber":"1428"}],"isLastPage":true,"pageCount":1,"pageNumber":1,"pageSize":10,"total":1}
     */

    private MonthWorkReportListBean monthWorkReportList;

    public MonthWorkReportListBean getMonthWorkReportList() {
        return monthWorkReportList;
    }

    public void setMonthWorkReportList(MonthWorkReportListBean monthWorkReportList) {
        this.monthWorkReportList = monthWorkReportList;
    }

    public static class MonthWorkReportListBean {
        /**
         * datas : [{"beginCreateDt":null,"beginTime":null,"collectiveLearningConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>3<\/p>\n<\/body>\n<\/html>","createCode":"1428","createName":"李晓娜","createTime":1573361667000,"dept":"中共山西省中条山国有林管理局侯马离退休支部委员会","deptCode":"10031","deptCommitteeConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>2<\/p>\n<\/body>\n<\/html>","deptNumberList":null,"deptPartyMeetingConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>1<\/p>\n<\/body>\n<\/html>","endCreateDt":null,"endTime":null,"groupByClause":null,"idList":null,"ifPass":0,"ifTop":1,"isDeleteText":null,"isEnableText":null,"monthWorkReportId":6,"orderByClause":null,"otherConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>5<\/p>\n<\/body>\n<\/html>","otherReportingMatters":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>6<\/p>\n<\/body>\n<\/html>","pageNumber":1,"pageSize":10,"remark":null,"startIndex":0,"themeDayConveneSituation":"<!DOCTYPE html>\n<html>\n<head>\n<\/head>\n<body>\n<p>4<\/p>\n<\/body>\n<\/html>","title":"555","topNum":null,"urlList":null,"userName":"李晓娜","userNumber":"1428"}]
         * isLastPage : true
         * pageCount : 1
         * pageNumber : 1
         * pageSize : 10
         * total : 1
         */

        private boolean isLastPage;
        private int pageCount;
        private int pageNumber;
        private int pageSize;
        private int total;
        private List<DatasBean> datas;

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * beginCreateDt : null
             * beginTime : null
             * collectiveLearningConveneSituation : <!DOCTYPE html>
             <html>
             <head>
             </head>
             <body>
             <p>3</p>
             </body>
             </html>
             * createCode : 1428
             * createName : 李晓娜
             * createTime : 1573361667000
             * dept : 中共山西省中条山国有林管理局侯马离退休支部委员会
             * deptCode : 10031
             * deptCommitteeConveneSituation : <!DOCTYPE html>
             <html>
             <head>
             </head>
             <body>
             <p>2</p>
             </body>
             </html>
             * deptNumberList : null
             * deptPartyMeetingConveneSituation : <!DOCTYPE html>
             <html>
             <head>
             </head>
             <body>
             <p>1</p>
             </body>
             </html>
             * endCreateDt : null
             * endTime : null
             * groupByClause : null
             * idList : null
             * ifPass : 0
             * ifTop : 1
             * isDeleteText : null
             * isEnableText : null
             * monthWorkReportId : 6
             * orderByClause : null
             * otherConveneSituation : <!DOCTYPE html>
             <html>
             <head>
             </head>
             <body>
             <p>5</p>
             </body>
             </html>
             * otherReportingMatters : <!DOCTYPE html>
             <html>
             <head>
             </head>
             <body>
             <p>6</p>
             </body>
             </html>
             * pageNumber : 1
             * pageSize : 10
             * remark : null
             * startIndex : 0
             * themeDayConveneSituation : <!DOCTYPE html>
             <html>
             <head>
             </head>
             <body>
             <p>4</p>
             </body>
             </html>
             * title : 555
             * topNum : null
             * urlList : null
             * userName : 李晓娜
             * userNumber : 1428
             */

            private Object beginCreateDt;
            private Object beginTime;
            private String collectiveLearningConveneSituation;
            private String createCode;
            private String createName;
            private long createTime;
            private String dept;
            private String deptCode;
            private String deptCommitteeConveneSituation;
            private Object deptNumberList;
            private String deptPartyMeetingConveneSituation;
            private Object endCreateDt;
            private Object endTime;
            private Object groupByClause;
            private Object idList;
            private int ifPass;
            private int ifTop;
            private Object isDeleteText;
            private Object isEnableText;
            private int monthWorkReportId;
            private Object orderByClause;
            private String otherConveneSituation;
            private String otherReportingMatters;
            private int pageNumber;
            private int pageSize;
            private Object remark;
            private int startIndex;
            private String themeDayConveneSituation;
            private String title;
            private Object topNum;
            private Object urlList;
            private String userName;
            private String userNumber;

            public Object getBeginCreateDt() {
                return beginCreateDt;
            }

            public void setBeginCreateDt(Object beginCreateDt) {
                this.beginCreateDt = beginCreateDt;
            }

            public Object getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(Object beginTime) {
                this.beginTime = beginTime;
            }

            public String getCollectiveLearningConveneSituation() {
                return collectiveLearningConveneSituation;
            }

            public void setCollectiveLearningConveneSituation(String collectiveLearningConveneSituation) {
                this.collectiveLearningConveneSituation = collectiveLearningConveneSituation;
            }

            public String getCreateCode() {
                return createCode;
            }

            public void setCreateCode(String createCode) {
                this.createCode = createCode;
            }

            public String getCreateName() {
                return createName;
            }

            public void setCreateName(String createName) {
                this.createName = createName;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getDept() {
                return dept;
            }

            public void setDept(String dept) {
                this.dept = dept;
            }

            public String getDeptCode() {
                return deptCode;
            }

            public void setDeptCode(String deptCode) {
                this.deptCode = deptCode;
            }

            public String getDeptCommitteeConveneSituation() {
                return deptCommitteeConveneSituation;
            }

            public void setDeptCommitteeConveneSituation(String deptCommitteeConveneSituation) {
                this.deptCommitteeConveneSituation = deptCommitteeConveneSituation;
            }

            public Object getDeptNumberList() {
                return deptNumberList;
            }

            public void setDeptNumberList(Object deptNumberList) {
                this.deptNumberList = deptNumberList;
            }

            public String getDeptPartyMeetingConveneSituation() {
                return deptPartyMeetingConveneSituation;
            }

            public void setDeptPartyMeetingConveneSituation(String deptPartyMeetingConveneSituation) {
                this.deptPartyMeetingConveneSituation = deptPartyMeetingConveneSituation;
            }

            public Object getEndCreateDt() {
                return endCreateDt;
            }

            public void setEndCreateDt(Object endCreateDt) {
                this.endCreateDt = endCreateDt;
            }

            public Object getEndTime() {
                return endTime;
            }

            public void setEndTime(Object endTime) {
                this.endTime = endTime;
            }

            public Object getGroupByClause() {
                return groupByClause;
            }

            public void setGroupByClause(Object groupByClause) {
                this.groupByClause = groupByClause;
            }

            public Object getIdList() {
                return idList;
            }

            public void setIdList(Object idList) {
                this.idList = idList;
            }

            public int getIfPass() {
                return ifPass;
            }

            public void setIfPass(int ifPass) {
                this.ifPass = ifPass;
            }

            public int getIfTop() {
                return ifTop;
            }

            public void setIfTop(int ifTop) {
                this.ifTop = ifTop;
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

            public int getMonthWorkReportId() {
                return monthWorkReportId;
            }

            public void setMonthWorkReportId(int monthWorkReportId) {
                this.monthWorkReportId = monthWorkReportId;
            }

            public Object getOrderByClause() {
                return orderByClause;
            }

            public void setOrderByClause(Object orderByClause) {
                this.orderByClause = orderByClause;
            }

            public String getOtherConveneSituation() {
                return otherConveneSituation;
            }

            public void setOtherConveneSituation(String otherConveneSituation) {
                this.otherConveneSituation = otherConveneSituation;
            }

            public String getOtherReportingMatters() {
                return otherReportingMatters;
            }

            public void setOtherReportingMatters(String otherReportingMatters) {
                this.otherReportingMatters = otherReportingMatters;
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

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public int getStartIndex() {
                return startIndex;
            }

            public void setStartIndex(int startIndex) {
                this.startIndex = startIndex;
            }

            public String getThemeDayConveneSituation() {
                return themeDayConveneSituation;
            }

            public void setThemeDayConveneSituation(String themeDayConveneSituation) {
                this.themeDayConveneSituation = themeDayConveneSituation;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getTopNum() {
                return topNum;
            }

            public void setTopNum(Object topNum) {
                this.topNum = topNum;
            }

            public Object getUrlList() {
                return urlList;
            }

            public void setUrlList(Object urlList) {
                this.urlList = urlList;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserNumber() {
                return userNumber;
            }

            public void setUserNumber(String userNumber) {
                this.userNumber = userNumber;
            }
        }
    }
}
