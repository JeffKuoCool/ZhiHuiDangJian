package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.develop;

import android.util.Log;
import android.view.View;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.BaseDevelopPartyFragment;
import com.lfc.zhihuidangjianapp.ui.activity.model.DevelopParty;
import com.lfc.zhihuidangjianapp.ui.activity.model.NativeDevelopParty;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date: 2019-09-02
 * @autror: guojian
 * @description:申请入党阶段
 */
public class ApplyPartyFragment extends BaseDevelopPartyFragment {

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
        params.put("submitApplicationTime1", parties.get(0).getContent());
        params.put("talkTime1", parties.get(1).getContent());
        params.put("talker1", parties.get(2).getContent());
    }

    private List<NativeDevelopParty> initPartys(){
        List<NativeDevelopParty> partyList= new ArrayList<>();
        partyList.add(new NativeDevelopParty(0,"递交入党申请书时间", ""));
        partyList.add(new NativeDevelopParty(0,"谈话时间", ""));
        partyList.add(new NativeDevelopParty(1,"谈话人", ""));
        return partyList;
    }

    public void setPartyData(DevelopParty developParty){
        List<NativeDevelopParty> partyList = parties;
        partyList.get(0).setContent(developParty.submitApplicationTime1==null?developParty.submitApplicationTime1:developParty.submitApplicationTime1.substring(0,10));
        partyList.get(1).setContent(developParty.talkTime1==null?developParty.talkTime1:developParty.talkTime1.substring(0,10));
        partyList.get(2).setContent(developParty.talker1);
        parties = partyList;
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
