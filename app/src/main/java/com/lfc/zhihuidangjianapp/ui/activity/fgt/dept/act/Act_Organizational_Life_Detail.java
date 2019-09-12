package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.DynamicDetail;
import com.lfc.zhihuidangjianapp.ui.activity.model.OrganizationalLife;
import com.lfc.zhihuidangjianapp.ui.activity.model.OrganizationalLifeDetail;
import com.lfc.zhihuidangjianapp.utlis.WebViewUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description: 组织生活详情
 */
public class Act_Organizational_Life_Detail extends BaseActivity {

    private String organizationalLifeId;

    private TextView tvTitle, tvAuthor, tvCreateTime, tv_time, tv_day,

    tv_place, tv_total_count, tv_count, tv_join_member, tv_type;
    private WebView tv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orgnization_life_detail;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        tvTitle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
        tvCreateTime = findViewById(R.id.tv_create_time);
        tv_time = findViewById(R.id.tv_time);
        tv_day = findViewById(R.id.tv_day);
        tv_place = findViewById(R.id.tv_place);
        tv_total_count = findViewById(R.id.tv_total_count);
        tv_count = findViewById(R.id.tv_count);
        tv_join_member = findViewById(R.id.tv_join_member);
        tv_content = findViewById(R.id.tv_content);
        tv_type = findViewById(R.id.tv_type);
    }

    @Override
    protected void initData() {
        organizationalLifeId = getIntent().getStringExtra("organizationalLifeId");
        Map<String, Object> map = new HashMap<>();
        map.put("organizationalLifeId", organizationalLifeId);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryOrganizationalLifeDetail(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<OrganizationalLifeDetail>(getActivity()) {

                    @Override
                    protected void onNext(OrganizationalLifeDetail response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        OrganizationalLife dynamic = response.getOrganizationalLife();
                        tvTitle.setText(dynamic.getTitle());
                        tvAuthor.setText(dynamic.getAuthor());
                        tvCreateTime.setText(dynamic.getReleaseDate());
                        tv_time.setText("时间："+dynamic.getStudyDate());
                        tv_day.setText("天数："+dynamic.getStudyDays()+"");
                        tv_place.setText("地点："+dynamic.getAddress());
                        tv_total_count.setText("应到人数："+dynamic.getArtyMembersCount()+"");
                        tv_count.setText("实到人数："+dynamic.getPartyMembershipCount()+"");
                        tv_join_member.setText(dynamic.getParticipants());
                     //   tv_content.setText(Html.fromHtml(dynamic.getComment()));
                        WebViewUtils.setWebView(dynamic.getComment(),tv_content);
                        tv_type.setText(dynamic.getStudyForm());
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
