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
 * @description: 发展对象的确定和考察阶段
 */
public class DevelopConfirmFragment extends BaseDevelopPartyFragment {

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
        params.put("partyGroupPassTime3", parties.get(0).getContent());
        params.put("partyBranchPassTime3", parties.get(1).getContent());
        params.put("branchCommitteePassTime3", parties.get(2).getContent());
        params.put("partyCommitteeResearchDecisionTime3", parties.get(3).getContent());
        params.put("defineJoinPartyPerson3", parties.get(4).getContent());
        params.put("talkTime3", parties.get(5).getContent());
        params.put("talkCount3", parties.get(6).getContent());
        params.put("checkPoliticalBackgroundTime3", parties.get(7).getContent());
        params.put("politicalConclusion3", parties.get(8).getContent());
        params.put("concentratedTrainingTime3", parties.get(9).getContent());
        params.put("trainingResults3", parties.get(10).getContent());
    }

    private List<NativeDevelopParty> initPartys(){
        List<NativeDevelopParty> partyList= new ArrayList<>();
        partyList.add(new NativeDevelopParty(0,"党小组通过时间", ""));
        partyList.add(new NativeDevelopParty(0,"党支部通过时间", ""));
        partyList.add(new NativeDevelopParty(0,"支委会通过时间", ""));
        partyList.add(new NativeDevelopParty(0,"党委研究决定时间", ""));
        partyList.add(new NativeDevelopParty(1,"确定入党介绍人", ""));
        partyList.add(new NativeDevelopParty(0,"谈话时间", ""));
        partyList.add(new NativeDevelopParty(1,"谈话次数", ""));
        partyList.add(new NativeDevelopParty(0,"政治审查时间", ""));
        partyList.add(new NativeDevelopParty(1,"政审结论", ""));
        partyList.add(new NativeDevelopParty(0,"集中培训时间", ""));
        partyList.add(new NativeDevelopParty(1,"培训成绩", ""));
        return partyList;
    }

    public void setPartyData(DevelopParty developParty){
        parties.get(0).setContent(developParty.partyGroupPassTime3==null?developParty.partyGroupPassTime3:developParty.partyGroupPassTime3.substring(0,10));
        parties.get(1).setContent(developParty.partyBranchPassTime3==null?developParty.partyBranchPassTime3:developParty.partyBranchPassTime3.substring(0,10));
        parties.get(2).setContent(developParty.branchCommitteePassTime3==null?developParty.branchCommitteePassTime3:developParty.branchCommitteePassTime3.substring(0,10));
        parties.get(3).setContent(developParty.partyCommitteeResearchDecisionTime3==null?developParty.partyCommitteeResearchDecisionTime3:developParty.partyCommitteeResearchDecisionTime3.substring(0,10));
        parties.get(4).setContent(developParty.defineJoinPartyPerson3);
        parties.get(5).setContent(developParty.talkTime3==null?developParty.talkTime3:developParty.talkTime3.substring(0,10));
        parties.get(6).setContent(developParty.talkCount3);
        parties.get(7).setContent(developParty.politicalConclusion3);
        parties.get(8).setContent(developParty.partyCommitteeResearchDecisionTime3==null?developParty.partyCommitteeResearchDecisionTime3:developParty.partyCommitteeResearchDecisionTime3.substring(0,10));
        parties.get(9).setContent(developParty.concentratedTrainingTime3==null?developParty.concentratedTrainingTime3:developParty.concentratedTrainingTime3.substring(0,10));
        parties.get(10).setContent(developParty.trainingResults3);
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

}
