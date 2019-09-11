package com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean;

import java.util.List;

public class MyInteagalBeabMingXI {


    /**
     * integralDetailList : {"datas":[{"beginCreateDt":null,"createCode":"1055","createName":null,"createTime":1568111327000,"endCreateDt":null,"groupByClause":null,"integralDetailId":334,"integralManagementId":242,"isDeleteText":null,"isEnableText":null,"orderByClause":null,"pageNumber":1,"pageSize":10,"remark":null,"startIndex":0,"total":5,"type":0,"userCode":"1055","userName":"徐盼"}],"isLastPage":true,"pageCount":1,"pageNumber":1,"pageSize":10,"total":1}
     */

    private IntegralDetailListBean integralDetailList;

    public IntegralDetailListBean getIntegralDetailList() {
        return integralDetailList;
    }

    public void setIntegralDetailList(IntegralDetailListBean integralDetailList) {
        this.integralDetailList = integralDetailList;
    }

    public static class IntegralDetailListBean {
        /**
         * datas : [{"beginCreateDt":null,"createCode":"1055","createName":null,"createTime":1568111327000,"endCreateDt":null,"groupByClause":null,"integralDetailId":334,"integralManagementId":242,"isDeleteText":null,"isEnableText":null,"orderByClause":null,"pageNumber":1,"pageSize":10,"remark":null,"startIndex":0,"total":5,"type":0,"userCode":"1055","userName":"徐盼"}]
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
             * createCode : 1055
             * createName : null
             * createTime : 1568111327000
             * endCreateDt : null
             * groupByClause : null
             * integralDetailId : 334
             * integralManagementId : 242
             * isDeleteText : null
             * isEnableText : null
             * orderByClause : null
             * pageNumber : 1
             * pageSize : 10
             * remark : null
             * startIndex : 0
             * total : 5
             * type : 0
             * userCode : 1055
             * userName : 徐盼
             */

            private Object beginCreateDt;
            private String createCode;
            private Object createName;
            private long createTime;
            private Object endCreateDt;
            private Object groupByClause;
            private int integralDetailId;
            private int integralManagementId;
            private Object isDeleteText;
            private Object isEnableText;
            private Object orderByClause;
            private int pageNumber;
            private int pageSize;
            private Object remark;
            private int startIndex;
            private int total;
            private int type;
            private String userCode;
            private String userName;

            public Object getBeginCreateDt() {
                return beginCreateDt;
            }

            public void setBeginCreateDt(Object beginCreateDt) {
                this.beginCreateDt = beginCreateDt;
            }

            public String getCreateCode() {
                return createCode;
            }

            public void setCreateCode(String createCode) {
                this.createCode = createCode;
            }

            public Object getCreateName() {
                return createName;
            }

            public void setCreateName(Object createName) {
                this.createName = createName;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public Object getEndCreateDt() {
                return endCreateDt;
            }

            public void setEndCreateDt(Object endCreateDt) {
                this.endCreateDt = endCreateDt;
            }

            public Object getGroupByClause() {
                return groupByClause;
            }

            public void setGroupByClause(Object groupByClause) {
                this.groupByClause = groupByClause;
            }

            public int getIntegralDetailId() {
                return integralDetailId;
            }

            public void setIntegralDetailId(int integralDetailId) {
                this.integralDetailId = integralDetailId;
            }

            public int getIntegralManagementId() {
                return integralManagementId;
            }

            public void setIntegralManagementId(int integralManagementId) {
                this.integralManagementId = integralManagementId;
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

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUserCode() {
                return userCode;
            }

            public void setUserCode(String userCode) {
                this.userCode = userCode;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
