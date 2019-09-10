package com.lfc.zhihuidangjianapp.ui.activity.model;

/**
 * @date: 2019-08-08
 * @autror: guojian
 * @description:
 */
public class ResponsePartyPayment {
    private String money;
    private String yearMonth;
    private String userName;
    private PartyPaymentHisList partyPaymentHisList;

    private int partyPaymentHisId;

    public int getPartyPaymentHisId() {
        return partyPaymentHisId;
    }

    public void setPartyPaymentHisId(int partyPaymentHisId) {
        this.partyPaymentHisId = partyPaymentHisId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PartyPaymentHisList getPartyPaymentHisList() {
        return partyPaymentHisList;
    }

    public void setPartyPaymentHisList(PartyPaymentHisList partyPaymentHisList) {
        this.partyPaymentHisList = partyPaymentHisList;
    }
}
