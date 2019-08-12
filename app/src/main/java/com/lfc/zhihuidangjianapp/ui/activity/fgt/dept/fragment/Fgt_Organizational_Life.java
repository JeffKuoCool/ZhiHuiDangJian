package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.BaseBindViewFragment;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Dept_Dynamic_Detail;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Organizational_Life_Detail;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.OrganizationalLife;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseOrganizationalLife;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponsePartyDynamicList;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description: 组织生活
 */
public class Fgt_Organizational_Life extends BaseBindViewFragment {

    RecyclerView recyclerView;

    private int studyType;

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initData() {
        studyType = getArguments().getInt("studyType", 0);
        Map<String, Object> map = new HashMap<>();
        map.put("studyType", studyType);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryOrganizationalLifePageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponseOrganizationalLife>(getActivity()) {

                    @Override
                    protected void onNext(ResponseOrganizationalLife response) {
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

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    private void setRecyclerView(ResponseOrganizationalLife response){
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<OrganizationalLife>(MyApplication.getAppContext(), R.layout.item_dept_dynamic, response.getOrganizationalLifeList().getDatas()) {
            @Override
            protected void convert(ViewHolder holder, OrganizationalLife data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                TextView tvContent = holder.getConvertView().findViewById(R.id.tv_content);
                tvContent.setText(Html.fromHtml(data.getComment()));
                ImageView image = holder.getConvertView().findViewById(R.id.image);
                String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                Glide.with(getActivity()).load(url).into(image);
                holder.getConvertView().setOnClickListener(item->{
                    Intent intent = new Intent(getActivity(), Act_Organizational_Life_Detail.class);
                    intent.putExtra("organizationalLifeId", data.getOrganizationalLifeId()+"");
                    startActivity(intent);
                });
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(MyApplication.getAppContext(), R.color.background),
                DispalyUtil.dp2px(MyApplication.getAppContext(), 1),
                0, 0, false
        ));
    }

}
