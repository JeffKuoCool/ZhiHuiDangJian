package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyCraftReportList;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-09-01
 * @autror: guojian
 * @description: 首页学习强局
 */
public class HomeStudyFragment extends BaseFragment {

    private RecyclerView recyclerView;

    //学习强局类型(0:林草大讲堂1:工匠培养2:学习心得)
    private int studyStrongBureauType;

    private int layoutId;

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    public static HomeStudyFragment getInstance(int studyStrongBureauType){
        HomeStudyFragment homeStudyFragment = new HomeStudyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("studyStrongBureauType", studyStrongBureauType);
        homeStudyFragment.setArguments(bundle);
        return homeStudyFragment;
    }

    @Override
    protected void initData() {
        studyStrongBureauType = getArguments().getInt("studyStrongBureauType");
        if(studyStrongBureauType==0){
            layoutId = R.layout.item_dept_dynamic;
        }else if(studyStrongBureauType==1){
            layoutId = R.layout.item_craftsman;
        }else if(studyStrongBureauType==2){
            layoutId = R.layout.item_study_report;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("studyStrongBureauType", studyStrongBureauType);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryStudyStrongBureauPageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<StudyCraftReportList>(getActivity()) {

                    @Override
                    protected void onNext(StudyCraftReportList response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                        setRecyclerView(response);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    public void setRecyclerView(StudyCraftReportList response) {
        //  林草大讲堂、工匠培养、学习心得三个内容，每个版块展示4条新闻，如需查看所有，点击更多
        if(studyStrongBureauType==0){
            //林草大课堂
            if (response.getStudyStrongBureauList().getDatas().size() >= 4) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(getActivity(), R.layout.item_dept_dynamic
                        , response.getStudyStrongBureauList().getDatas().subList(0,4)) {
                    @Override
                    protected void convert(ViewHolder holder, StudyStrongBureau data, int position) {
                        TextView title = holder.getConvertView().findViewById(R.id.tv_title);
                        title.setText(Html.fromHtml(data.getComment()));
                        holder.setText(R.id.tv_bottom, data.getTitle());
                        TextView tvContent = holder.getConvertView().findViewById(R.id.tv_content);
                        tvContent.setText(data.getReleaseDate());
                        ImageView image = holder.getConvertView().findViewById(R.id.image);
                        String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                        Glide.with(getActivity()).load(url).into(image);
                        holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                            Intent intent = new Intent(getActivity(), com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Strong_Study_Experience.class);
                            intent.putExtra("studyStrongBureauId", data.getStudyStrongBureauId()+"");
                            intent.putExtra("appTitle","林草大课堂");
                            startActivity(intent);
                        });
                    }
                });
            }else{
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(getActivity(), R.layout.item_dept_dynamic
                        , response.getStudyStrongBureauList().getDatas()) {
                    @Override
                    protected void convert(ViewHolder holder, StudyStrongBureau data, int position) {
                        TextView title = holder.getConvertView().findViewById(R.id.tv_title);
                        title.setText(Html.fromHtml(data.getComment()));
                        holder.setText(R.id.tv_bottom, data.getTitle());
                        TextView tvContent = holder.getConvertView().findViewById(R.id.tv_content);
                        tvContent.setText(data.getReleaseDate());
                        ImageView image = holder.getConvertView().findViewById(R.id.image);
                        String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                        Glide.with(getActivity()).load(url).into(image);
                        holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                            Intent intent = new Intent(getActivity(), com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Strong_Study_Experience.class);
                            intent.putExtra("studyStrongBureauId", data.getStudyStrongBureauId()+"");
                            intent.putExtra("appTitle","林草大课堂");
                            startActivity(intent);
                        });
                    }

                });
            }

        }else if(studyStrongBureauType==1){
            //工匠培养
            if (response.getStudyStrongBureauList().getDatas().size() >= 4) {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(getActivity(), R.layout.item_craftsman
                        , response.getStudyStrongBureauList().getDatas().subList(0, 4)) {
                    @Override
                    protected void convert(ViewHolder holder, StudyStrongBureau data, int position) {
                        holder.setText(R.id.tv_title, data.getTitle());
                        ImageView image = holder.getConvertView().findViewById(R.id.image);
                        String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                        Glide.with(getActivity()).load(url).into(image);
                        holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                            Intent intent = new Intent(getActivity(), com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Strong_Study_Experience.class);
                            intent.putExtra("studyStrongBureauId", data.getStudyStrongBureauId()+"");
                            intent.putExtra("appTitle","工匠培养");
                            startActivity(intent);
                        });
                    }

                });
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(getActivity(), R.layout.item_craftsman
                        , response.getStudyStrongBureauList().getDatas()) {
                    @Override
                    protected void convert(ViewHolder holder, StudyStrongBureau data, int position) {
                        holder.setText(R.id.tv_title, data.getTitle());
                        ImageView image = holder.getConvertView().findViewById(R.id.image);
                        String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                        Glide.with(getActivity()).load(url).into(image);
                        holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                            Intent intent = new Intent(getActivity(), com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Strong_Study_Experience.class);
                            intent.putExtra("studyStrongBureauId", data.getStudyStrongBureauId()+"");
                            intent.putExtra("appTitle","工匠培养");
                            startActivity(intent);
                        });
                    }

                });
            }


        }else if(studyStrongBureauType==2){
            //学习心得
            if (response.getStudyStrongBureauList().getDatas().size() >= 4) {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(getActivity(), R.layout.item_study_report
                        , response.getStudyStrongBureauList().getDatas().subList(0,4)) {
                    @Override
                    protected void convert(ViewHolder holder, StudyStrongBureau data, int position) {
                        ImageView image = holder.getConvertView().findViewById(R.id.image);
                        String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                        Glide.with(getActivity()).load(url).into(image);
                        holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                            Intent intent = new Intent(getActivity(), com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Strong_Study_Experience.class);
                            intent.putExtra("studyStrongBureauId", data.getStudyStrongBureauId()+"");
                            intent.putExtra("appTitle","学习心得");
                            startActivity(intent);
                        });
                    }

                });
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(getActivity(), R.layout.item_study_report
                        , response.getStudyStrongBureauList().getDatas()) {
                    @Override
                    protected void convert(ViewHolder holder, StudyStrongBureau data, int position) {
                        ImageView image = holder.getConvertView().findViewById(R.id.image);
                        String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                        Glide.with(getActivity()).load(url).into(image);
                        holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                            Intent intent = new Intent(getActivity(), com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Strong_Study_Experience.class);
                            intent.putExtra("studyStrongBureauId", data.getStudyStrongBureauId()+"");
                            intent.putExtra("appTitle","学习心得");
                            startActivity(intent);
                        });
                    }

                });
            }


        }
    }
}
