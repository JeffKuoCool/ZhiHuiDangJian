package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.develop;

import android.view.View;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.BaseDevelopPartyFragment;
import com.lfc.zhihuidangjianapp.ui.activity.model.DevelopParty;
import com.lfc.zhihuidangjianapp.ui.activity.model.NativeDevelopParty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date: 2019-09-02
 * @autror: guojian
 * @description: 申请积极分子的确定和培养教育阶段
 */
public class ApplyEducationPartyFragment extends BaseDevelopPartyFragment {

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
        params.put("recommendationMode2", parties.get(0).getContent());
        params.put("partyGroupPassTime2", parties.get(1).getContent());
        params.put("branchCommitteePassTime2", parties.get(2).getContent());
        params.put("designatedCulturePerson2", parties.get(3).getContent());
        params.put("keepRecordTime2", parties.get(4).getContent());
        params.put("partyCommitteeExaminationTime2", parties.get(5).getContent());
    }

    private List<NativeDevelopParty> initPartys(){
        List<NativeDevelopParty> partyList= new ArrayList<>();
        partyList.add(new NativeDevelopParty(1,"推荐方式", ""));
        partyList.add(new NativeDevelopParty(0,"党小组通过时间", ""));
        partyList.add(new NativeDevelopParty(0,"支委会通过时间", ""));
        partyList.add(new NativeDevelopParty(1,"指定培养联系人", ""));
        partyList.add(new NativeDevelopParty(0,"报党委备案时间", ""));
        partyList.add(new NativeDevelopParty(0,"党委审查时间", ""));
        return partyList;
    }

    public void setPartyData(DevelopParty developParty){
        parties.get(0).setContent(developParty.recommendationMode2);
        parties.get(1).setContent(developParty.partyGroupPassTime2==null?developParty.partyGroupPassTime2:developParty.partyGroupPassTime2.substring(0,10));
        parties.get(2).setContent(developParty.branchCommitteePassTime2==null?developParty.branchCommitteePassTime2:developParty.branchCommitteePassTime2.substring(0,10));
        parties.get(3).setContent(developParty.designatedCulturePerson2);
        parties.get(4).setContent(developParty.keepRecordTime2==null?developParty.keepRecordTime2:developParty.keepRecordTime2.substring(0,10));
        parties.get(5).setContent(developParty.partyCommitteeExaminationTime2==null?developParty.partyCommitteeExaminationTime2:developParty.partyCommitteeExaminationTime2.substring(0,10));
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
