package com.lfc.zhihuidangjianapp.net.http;

import com.lfc.zhihuidangjianapp.BuildConfig;

public class ApiConstant {
    //正试环境
    public static final String ROOT_RELEASE = "https://dj.sxzts.cn/";
    //测试环境
     public static final String ROOT_DEBUG = "http://58.87.96.160:8081/";
    //  public static final String ROOT_DEBUG = "http://192.168.1.19:8081/";

    public static final String ROOT_URL =
            BuildConfig.DEBUG ?
            ROOT_DEBUG :
                    ROOT_RELEASE;

    public static final String API = "appApi";

    public static final String MEETING = "meeting";

    public static final String HUANXIN = "huanxin";

    //登录
    public static final String LOGIN = "login/login";
    //分页公告信息
    public static final String queryHomeNoticeAnnouncementPageList = "appApi/queryHomeNoticeAnnouncementPageList";
    // app-Banner
    public static final String queryAppConfigList = "appApi/queryAppConfigList";
    // 查看公告详情信息
    public static final String queryNoticeAnnouncementDetail = "appApi/queryNoticeAnnouncementDetail";
    // 查看公告详情信息
    public static final String queryNoticeAnnouncementPageList = "appApi/queryNoticeAnnouncementPageList";
    // 通讯录-按首字母查询
    public static final String queryUserListByFirstPinYin = "appApi/queryUserListByFirstPinYin";
    // 专题专栏
    public static final String queryLeadDemonstrationPageList = "appApi/queryLeadDemonstrationPageList";
    // 我的党费
    public static final String queryMyPartyPaymentHisPageList = "appApi/queryMyPartyPaymentHisPageList";
    // 查询发展党员信息
    public static final String queryJoinPartyStageDeatil = "appApi/queryJoinPartyStageDeatil";
    // 分页查询党费缴费记录信息-已缴列表
    public static final String queryPartyPaymentHisPageList = "appApi/queryPartyPaymentHisPageList";

    // 分页查询党费缴费记录信息-已缴列表
    public static final String captcha = "login/captcha";
}

