package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.develop;

import android.view.View;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.BaseDevelopPartyFragment;
import com.lfc.zhihuidangjianapp.ui.activity.model.DevelopParty;
import com.lfc.zhihuidangjianapp.ui.activity.model.NativeDevelopParty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @date: 2019-09-02
 * @autror: guojian
 * @description: 预备党员的接收阶段
 */
public class PrepareReceiveFragment extends BaseDevelopPartyFragment {

    @Override
    public List<NativeDevelopParty> getParties() {
        return initPartys();
    }

    @Override
    public void submit(List<NativeDevelopParty> parties) {
        setParams();
        saveData(params);
    }

    @Override
    protected void initData() {
        super.initData();
        setParams();
    }

    public void setParams(){
        params = new HashMap<>();
        params.put("subcommitteeReviewTime4", parties.get(0).getContent());
        params.put("publicityTime4", parties.get(1).getContent());
        params.put("parentPartyCommitteePretrialTime4", parties.get(2).getContent());
        params.put("branchConferencePassTime4", parties.get(3).getContent());
        params.put("parentPartyCommitteeTalkTime4", parties.get(4).getContent());
        params.put("talker4", parties.get(5).getContent());
        params.put("parentPartyCommitteePassTime4", parties.get(6).getContent());
        params.put("meetingTime4", parties.get(7).getContent());
        params.put("partyMembersCount4", parties.get(8).getContent());
        params.put("partyMembershipCount4", parties.get(9).getContent());
        params.put("sendVotes4", parties.get(10).getContent());
        params.put("takeBackVotes4", parties.get(11).getContent());
        params.put("agreeVotes4", parties.get(12).getContent());
    }

    public void setPartyData(DevelopParty developParty){
        parties.get(0).setContent(developParty.subcommitteeReviewTime4==null?developParty.subcommitteeReviewTime4:developParty.subcommitteeReviewTime4.substring(0,10));
        parties.get(1).setContent(developParty.publicityTime4==null?developParty.publicityTime4:developParty.publicityTime4.substring(0,10));
        parties.get(2).setContent(developParty.parentPartyCommitteePretrialTime4==null?developParty.parentPartyCommitteePretrialTime4:developParty.parentPartyCommitteePretrialTime4.substring(0,10));
        parties.get(3).setContent(developParty.branchConferencePassTime4==null?developParty.branchConferencePassTime4:developParty.branchConferencePassTime4.substring(0,10));
        parties.get(4).setContent(developParty.parentPartyCommitteeTalkTime4==null?developParty.parentPartyCommitteeTalkTime4:developParty.parentPartyCommitteeTalkTime4.substring(0,10));
        parties.get(5).setContent(developParty.talker4);
        parties.get(6).setContent(developParty.parentPartyCommitteePassTime4==null?developParty.parentPartyCommitteePassTime4:developParty.parentPartyCommitteePassTime4.substring(0,10));
        parties.get(7).setContent(developParty.meetingTime4==null?developParty.meetingTime4:developParty.meetingTime4.substring(0,10));
        parties.get(8).setContent(developParty.partyMembersCount4);
        parties.get(9).setContent(developParty.partyMembershipCount4);
        parties.get(10).setContent(developParty.sendVotes4);
        parties.get(11).setContent(developParty.takeBackVotes4);
        parties.get(12).setContent(developParty.agreeVotes4);
        if(developParty.submitStatus == 1){
            mRootView.findViewById(R.id.tvSave).setVisibility(View.GONE);
            for (NativeDevelopParty party: parties){
                party.status = 1;
                party.setStyleId(0);
            }
        }else{
            mRootView.findViewById(R.id.tvSave).setVisibility(View.VISIBLE);
        }
        setRecyclerView();
    }

    private List<NativeDevelopParty> initPartys(){
        List<NativeDevelopParty> partyList= new ArrayList<>();
        partyList.add(new NativeDevelopParty(0,"支委会审查时间", ""));
        partyList.add(new NativeDevelopParty(0,"公示时间", ""));
        partyList.add(new NativeDevelopParty(0,"上级党委预审时间", ""));
        partyList.add(new NativeDevelopParty(0,"支部大会通过时间", ""));
        partyList.add(new NativeDevelopParty(0,"上级党委谈话时间", ""));
        partyList.add(new NativeDevelopParty(1,"谈话人", ""));
        partyList.add(new NativeDevelopParty(0,"上级党委通过时间", ""));
        partyList.add(new NativeDevelopParty(0,"会议时间", ""));
        partyList.add(new NativeDevelopParty(1,"应到会党员数", ""));
        partyList.add(new NativeDevelopParty(1,"实到会党员数", ""));
        partyList.add(new NativeDevelopParty(1,"发出票数", ""));
        partyList.add(new NativeDevelopParty(1,"收回票数", ""));
        partyList.add(new NativeDevelopParty(1,"同意票数", ""));
        return partyList;
    }

}
