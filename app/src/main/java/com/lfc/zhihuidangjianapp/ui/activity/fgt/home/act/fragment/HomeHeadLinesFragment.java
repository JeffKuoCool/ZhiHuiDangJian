package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Dept_Dynamic_Detail;
import com.lfc.zhihuidangjianapp.ui.activity.model.HomeHead;
import com.lfc.zhihuidangjianapp.ui.activity.model.HomeHeadLines;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-29
 * @autror: guojian
 * @description:
 */
public class HomeHeadLinesFragment extends BaseFragment {

    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    private void setRecyclerView(HomeHeadLines response){
        if (response.getExamineList().size() >= 3) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new CommonAdapter<HomeHead>(recyclerView.getContext(), R.layout.item_home_head, response.getExamineList()) {
                @Override
                protected void convert(ViewHolder holder, HomeHead data, int position) {
                    holder.setText(R.id.tv_title, data.getTitle());
                    holder.setText(R.id.tv_content, data.getReleaseDate());
                    ImageView image = holder.getConvertView().findViewById(R.id.image);
                    Glide.with(image.getContext()).load(ApiConstant.ROOT_URL+data.getUrl()).into(image);
                    //点击列表进入党建动态详情
                    holder.getConvertView().setOnClickListener(item->{
                        Intent intent = new Intent(MyApplication.getAppContext(), Act_Dept_Dynamic_Detail.class);
                        intent.putExtra("examineId", data.getExamineId()+"");
                        intent.putExtra("type",data.getArticleType()+"");
                        intent.putExtra("partyDynamicId","");
                        startActivity(intent);
                    });
                }

            });
            recyclerView.addItemDecoration(new DividerItemDecoration(
                    DividerItemDecoration.VERTICAL_LIST,
                    ContextCompat.getColor(MyApplication.getAppContext(), R.color.background),
                    DispalyUtil.dp2px(MyApplication.getAppContext(), 3),
                    0, 0, false
            ));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new CommonAdapter<HomeHead>(recyclerView.getContext(), R.layout.item_home_head, response.getExamineList()) {
                @Override
                protected void convert(ViewHolder holder, HomeHead data, int position) {
                    holder.setText(R.id.tv_title, data.getTitle());
                    holder.setText(R.id.tv_content, data.getReleaseDate());
                    ImageView image = holder.getConvertView().findViewById(R.id.image);
                    Glide.with(image.getContext()).load(ApiConstant.ROOT_URL+data.getUrl()).into(image);
                    //点击列表进入党建动态详情
                    holder.getConvertView().setOnClickListener(item->{
                        Intent intent = new Intent(MyApplication.getAppContext(), Act_Dept_Dynamic_Detail.class);
                        intent.putExtra("examineId", data.getExamineId()+"");
                        intent.putExtra("type",data.getArticleType()+"");
                        intent.putExtra("partyDynamicId","");
                        startActivity(intent);
                    });
                }

            });
            recyclerView.addItemDecoration(new DividerItemDecoration(
                    DividerItemDecoration.VERTICAL_LIST,
                    ContextCompat.getColor(MyApplication.getAppContext(), R.color.background),
                    DispalyUtil.dp2px(MyApplication.getAppContext(), 3),
                    0, 0, false
            ));
        }
    }

    @Override
    protected void initData() {
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryHomeHeadLinesList(MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<HomeHeadLines>(getActivity()) {

                    @Override
                    protected void onNext(HomeHeadLines response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        setRecyclerView(response);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
