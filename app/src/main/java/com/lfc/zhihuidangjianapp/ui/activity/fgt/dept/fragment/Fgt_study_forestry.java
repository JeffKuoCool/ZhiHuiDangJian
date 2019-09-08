package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.QueryPopBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyCraftReportList;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyCraftTrainingList;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @date: 2019-08-10
 * @autror: guojian
 * @description: 林草公开课
 */
public class Fgt_study_forestry extends BaseFragment {
    private RecyclerView recyclerView;

    private int studyStrongBureauType;

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("studyStrongBureauType", 0);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryStudyStrongBureauConsultationPageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<StudyCraftTrainingList>(getActivity()) {

                    @Override
                    protected void onNext(StudyCraftTrainingList response) {
                        Log.e("onNext= ", response.toString() +"=="+ response.getStudyStrongBureauCraftsmanList().getDatas().size());
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
        toast(response.getStudyStrongBureauCraftsmanList().getTotal());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(MyApplication.getAppContext(), R.layout.item_dept_dynamic, response.getStudyStrongBureauCraftsmanList().getDatas()) {
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
                    intent.putExtra("appTitle", "林草公开课");
                    startActivity(intent);
                });
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.white),
                DispalyUtil.dp2px(getActivity(), 8),
                0, 0, false
        ));
    }
}