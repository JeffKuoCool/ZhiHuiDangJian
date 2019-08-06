package com.lfc.zhihuidangjianapp.ui.activity.fgt;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Branch_lead;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Craftsman_Training;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Forestry_Course;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Government_Build;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Change;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Example;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Life;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Member_Practice;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Pay;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_United_Front;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Weekend_Report;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_PartyBuildingMatrix;
import com.lfc.zhihuidangjianapp.ui.activity.model.NativePartyBody;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 党务工作
 */
public class Fgt_PartyAffairs extends BaseFragment {

    private RecyclerView recyclerView;

    private int count = 4;

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview_with_appbar;
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.imgBack).setVisibility(View.GONE);
        TextView title = rootView.findViewById(R.id.tv_title);
        title.setText("党务工作");
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter<List<NativePartyBody.PartBody>>(getActivity(), R.layout.item_party, new NativePartyBody().getPartBodys()) {

            @Override
            protected void convert(ViewHolder holder, List<NativePartyBody.PartBody> partBodies, int position) {
                holder.setText(R.id.tv_title, partBodies.get(0).title);
                RecyclerView itemRecyclerView = holder.getConvertView().findViewById(R.id.item_recyclerview);
                setItemRecyclerView(partBodies, itemRecyclerView, position);
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.background),
                DispalyUtil.dp2px(getActivity(), 1),
                0, 0, false
        ));
    }

    private void setItemRecyclerView(List<NativePartyBody.PartBody> partBodies, RecyclerView recyclerView, int parentIndex){
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), count));
        recyclerView.setAdapter(new CommonAdapter<NativePartyBody.PartBody>(getActivity(), R.layout.item_home_func, partBodies) {

            @Override
            protected void convert(ViewHolder holder, NativePartyBody.PartBody data, int position) {
                holder.setText(R.id.text, data.text);
                holder.setImageDrawable(R.id.icon, getResources().getDrawable(data.imageRes));
                holder.getConvertView().setOnClickListener(item->{
                    itemClick(parentIndex, position);
                });
            }

        });
    }

    /**
     * 事件源
     * @param parentIndex
     * @param position
     */
    private void itemClick(int parentIndex, int position){
        switch (parentIndex){
            case 0://党建平台
                partPlatform(position);
                break;
            case 1://党建资讯
                partInformation(position);
                break;
            case 2://示范引领
                partGuide(position);
                break;
            case 3://学习强局
                partStrong(position);
                break;
            case 4://林区风采
                partTree(position);
                break;
            case 5://通讯平台
                partCommunication(position);
                break;
        }
    }

    /**
     * 党建平台事件响应
     * @param position
     */
    private void partPlatform(int position){
        switch (position){
            case 0://党建矩阵
                startActivity(new Intent(getActivity(), Act_PartyBuildingMatrix.class));
                break;
            case 1://党费缴纳
                startActivity(new Intent(getActivity(), Act_Party_Pay.class));
                break;
            case 2://党关系转让
                startActivity(new Intent(getActivity(), Act_Party_Change.class));
                break;
            case 3://组织生活
                startActivity(new Intent(getActivity(), Act_Party_Life.class));
                break;
            case 4://工作周报
                startActivity(new Intent(getActivity(), Act_Weekend_Report.class));
                break;
        }
    }

    /**
     * 党建资讯
     * @param position
     */
    private void partInformation(int position){
        switch (position){
            case 0://党建矩阵 TODO
                startActivity(new Intent(getActivity(), Act_PartyBuildingMatrix.class));
                break;
            case 1://群团统战
                startActivity(new Intent(getActivity(), Act_United_Front.class));
                break;
            case 2://廉政建设
                startActivity(new Intent(getActivity(), Act_Government_Build.class));
                break;
        }
    }

    /**
     * 示范引领
     * @param position
     */
    private void partGuide(int position){
        switch (position){
            case 0://党委示范
                startActivity(new Intent(getActivity(), Act_Party_Example.class));
                break;
            case 1://支部引领
                startActivity(new Intent(getActivity(), Act_Branch_lead.class));
                break;
            case 2://党员实践
                startActivity(new Intent(getActivity(), Act_Party_Member_Practice.class));
                break;
        }
    }

    /**
     * 学习强局
     * @param position
     */
    private void partStrong(int position){
        switch (position){
            case 0://林草公开课
                startActivity(new Intent(getActivity(), Act_Forestry_Course.class));
                break;
            case 1://工匠培养
                startActivity(new Intent(getActivity(), Act_Craftsman_Training.class));
                break;
            case 2://学习心得

                break;
        }
    }

    /**
     * 林区风采
     * @param position
     */
    private void partTree(int position){
        switch (position){
            case 0://先进基层党

                break;
            case 1://优秀党务工作者

                break;
            case 2://优秀共产党

                break;
            case 3://党建联系员

                break;
        }
    }

    /**
     * 通讯平台
     * @param position
     */
    private void partCommunication(int position){
        switch (position){
            case 0://通讯录

                break;
            case 1://会议连线

                break;
        }
    }

}
