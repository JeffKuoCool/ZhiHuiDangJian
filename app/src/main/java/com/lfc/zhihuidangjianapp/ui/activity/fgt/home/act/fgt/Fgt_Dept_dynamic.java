package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fgt;

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
import com.lfc.zhihuidangjianapp.ui.activity.item.BannerViewHolder;
import com.lfc.zhihuidangjianapp.ui.activity.model.AppConfigLists;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponsePartyDynamicList;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description:
 */
public class Fgt_Dept_dynamic extends BaseBindViewFragment {

    RecyclerView recyclerView;

    private int partyDynamicType;

    private Banner banner;

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview_with_banner;
    }

    @Override
    protected void initData() {
        partyDynamicType = getArguments().getInt("partyDynamicType", 0);
        Map<String, Object> map = new HashMap<>();
        map.put("partyDynamicType", partyDynamicType);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryPartyDynamicPageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponsePartyDynamicList>(MyApplication.getAppContext()) {

                    @Override
                    protected void onNext(ResponsePartyDynamicList response) {
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
        queryAppConfigList();
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        banner = rootView.findViewById(R.id.banner);
    }

    public void queryAppConfigList() {
        Map<String, String> map = new HashMap<>();
        map.put("ifBanner", "1");
        map.put("position", "1");
        map.put("type", "1");
        map.put("number", partyDynamicType+"");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryAppConfigList(map,MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<AppConfigLists>(getActivity()) {

                    @Override
                    protected void onNext(AppConfigLists response) {
                        setBanner(response);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    private void setBanner(AppConfigLists response) {
        banner.setImages(response.getAppConfigList().getDatas()).setImageLoader(new BannerViewHolder()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    private void setRecyclerView(ResponsePartyDynamicList response){
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<Dynamic>(MyApplication.getAppContext(), R.layout.item_dept_dynamic, response.getPartyDynamicList().getDatas()) {
            @Override
            protected void convert(ViewHolder holder, Dynamic data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                TextView tvContent = holder.getConvertView().findViewById(R.id.tv_content);
                tvContent.setText(Html.fromHtml(data.getComment()));
//                tvContent.setHtml(data.getComment(),
//                        new HtmlAssetsImageGetter(tvContent));
                ImageView image = holder.getConvertView().findViewById(R.id.image);
                String url = ApiConstant.ROOT_URL+data.getThumbnail_url();
                Glide.with(MyApplication.getAppContext()).load(url).into(image);
                holder.getConvertView().setOnClickListener(item->{
                    Intent intent = new Intent(MyApplication.getAppContext(), Act_Dept_Dynamic_Detail.class);
                    intent.putExtra("partyDynamicId", data.getParty_dynamic_id()+"");
                    startActivity(intent);
                });
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(MyApplication.getAppContext(), R.color.divider_list),
                DispalyUtil.dp2px(MyApplication.getAppContext(), 5),
                0, 0, false
        ));
    }

}
