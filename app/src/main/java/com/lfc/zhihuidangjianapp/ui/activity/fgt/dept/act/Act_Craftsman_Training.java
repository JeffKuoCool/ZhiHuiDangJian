package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyCraftTrainingList;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureauDetail;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-05
 * @autror: guojian
 * @description: 工匠培养
 */
public class Act_Craftsman_Training extends BaseActivity {

    private RecyclerView recyclerView;

    private TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview_with_appbar;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        recyclerView = findViewById(R.id.recyclerView);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("工匠培养");
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
    }

    @Override
    protected void initData() {
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryStudyStrongBureauConsultationPageList( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<StudyCraftTrainingList>(getActivity()) {

                    @Override
                    protected void onNext(StudyCraftTrainingList response) {
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

    public void setRecyclerView(StudyCraftTrainingList response) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(Act_Craftsman_Training.this, R.layout.item_dept_dynamic, response.getStudyStrongBureauCraftsmanList().getDatas()) {
            @Override
            protected void convert(ViewHolder holder, StudyStrongBureau data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                TextView tvContent = holder.getConvertView().findViewById(R.id.tv_content);
                tvContent.setText(Html.fromHtml(data.getComment()));
                ImageView image = holder.getConvertView().findViewById(R.id.image);
                String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                Glide.with(getActivity()).load(url).into(image);

                holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                    Intent intent = new Intent(getActivity(), com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Strong_Study_Experience.class);
                    intent.putExtra("studyStrongBureauId", data.getStudyStrongBureauId()+"");
                    startActivity(intent);
                });
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.divider_list),
                DispalyUtil.dp2px(getActivity(), 5),
                0, 0, false
        ));
    }
}
