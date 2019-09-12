package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
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
import com.lfc.zhihuidangjianapp.ui.activity.model.Forest;
import com.lfc.zhihuidangjianapp.ui.activity.model.ForestDetail;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseForestDetail;
import com.lfc.zhihuidangjianapp.utlis.WebViewUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-09-02
 * @autror: guojian
 * @description: 林区风采详情
 */
public class ForestDetailActivity extends BaseActivity {

    private Forest forest;

    private TextView tv_title, tv_content;
    private WebView webView;

    private ImageView image;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forest_detail;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        forest = (Forest)getIntent().getSerializableExtra("forest");
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        image = findViewById(R.id.image);
        webView=findViewById(R.id.webView);
    }

    @Override
    protected void initData() {
        if(forest==null){
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("forestDistrictId", forest.getForestDistrictId());
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryForestDistrictDetail(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponseForestDetail>(getActivity()) {

                    @Override
                    protected void onNext(ResponseForestDetail response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        ForestDetail detail = response.getForestDistrict();
                        tv_title.setText(detail.getRemark());
                        WebViewUtils.setWebView(detail.getComment(),webView);
                        String url = ApiConstant.ROOT_URL+detail.getAuthor();

                        Glide.with(getActivity()).load(url).into(image);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
