package com.lfc.zhihuidangjianapp.ui.activity.model;

import java.util.List;

/**
 * @date: 2019-08-03
 * @autror: guojian
 * @description:
 */
public class DeptDetail {
    private Dept dept;
    private List<DeptDetailUser> userlist;
    private List<String> directorNameList;
    //组织生活
    private List<OLisfForEacherList> oListForEacherList;
    //周报
    private List<OLisfForEacherList> weekEacherList;

    public List<OLisfForEacherList> getoLisfForEacherList() {
        return oListForEacherList;
    }

    public List<OLisfForEacherList> getWeekEacherList() {
        return weekEacherList;
    }

    public void setWeekEacherList(List<OLisfForEacherList> weekEacherList) {
        this.weekEacherList = weekEacherList;
    }

    public void setoLisfForEacherList(List<OLisfForEacherList> oLisfForEacherList) {
        this.oListForEacherList = oLisfForEacherList;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public List<DeptDetailUser> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<DeptDetailUser> userlist) {
        this.userlist = userlist;
    }

    public List<String> getDirectorNameList() {
        return directorNameList;
    }

    public void setDirectorNameList(List<String> directorNameList) {
        this.directorNameList = directorNameList;
    }
    public static class  OLisfForEacherList{
        private int articalCount;
        private int month;

        public OLisfForEacherList(int articalCount, int month) {
            this.articalCount = articalCount;
            this.month = month;
        }

        public OLisfForEacherList() {
        }

        public int getArticalCount() {
            return articalCount;
        }

        public void setArticalCount(int articalCount) {
            this.articalCount = articalCount;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }
    }
}
