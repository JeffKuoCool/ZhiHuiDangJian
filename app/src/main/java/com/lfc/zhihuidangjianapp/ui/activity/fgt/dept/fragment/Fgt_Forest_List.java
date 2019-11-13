package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.BaseBindViewFragment;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Dept_Dynamic_Detail;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.ForestDetailActivity;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.Forest;
import com.lfc.zhihuidangjianapp.ui.activity.model.ForestDistrict;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponsePartyDynamicList;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description:
 */
public class Fgt_Forest_List extends BaseBindViewFragment {

    RecyclerView recyclerView;

    private Unbinder unbinder;

    private int partyDynamicType;

    private int layoutId;
    private int size=10;
    private int num=1;
    private boolean isData = true;
    SmartRefreshLayout mRefreshLayout;
    List<Forest> datas_new = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initData() {
        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(false));
        //设置 Footer 为 普通样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        //设置 Header 为 普通样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        refresh();
        getDatas();

    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
    }

    private void refresh() {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                num = 1;
                getDatas();
                refreshlayout.finishRefresh(2000);

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (!isData) {
                    //                mDefineBAGLoadView.setFooterTextOrImage("没有更多数据", false);
                } else {
                    //                    mDefineBAGLoadView.setFooterTextOrImage("正在玩命加载中...", true);
                    num++;
                    getDatas();
                }
                refreshLayout.finishLoadMore(2000);
            }
        });

    }
    private void getDatas() {
        partyDynamicType = getArguments().getInt("leadDemonstrationType", 0);
        if (partyDynamicType == 0) {
            layoutId = R.layout.item_fine_party_group;
        } else {
            layoutId = R.layout.item_forest_list;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("forestDistrictType", partyDynamicType);
        map.put("pageSize", size + "");
        map.put("pageNumber", num + "");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryForestShowPageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ForestDistrict>(getActivity()) {

                    @Override
                    protected void onNext(ForestDistrict response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        // setRecyclerView(response);
                        List<Forest> datas = response.getForestDistrictList().getDatas();
                        Log.i("yy00datas",datas.size()+"");
                        if (num == 1) {
                            datas_new.clear();
                        }
                        if (datas.size() == 0) {
                            if (num == 1 && datas_new.size() == 0) {
                                isData = true;
                                num--;
                            } else {
                                isData = false;
                            }
                        } else {
                            datas_new.addAll(datas);
                            if (datas.size() < 8) {
                                isData = false;
                            } else {
                                isData = true;
                            }
                            setRecyclerView( datas_new);
                        }
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());


    }

    private void setRecyclerView(List<Forest> response) {
        if (partyDynamicType == 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new CommonAdapter<Forest>(getActivity(), layoutId, response) {
                @Override
                protected void convert(ViewHolder holder, Forest data, int position) {
                    TextView tvContent = holder.getConvertView().findViewById(R.id.tv_content);
                    tvContent.setText(data.getDeptName());
                    ImageView image = holder.getConvertView().findViewById(R.id.image);
                    String url = ApiConstant.ROOT_URL + data.getLogo();
                    Glide.with(getActivity()).load(url).into(image);
                    holder.getConvertView().setOnClickListener(detail->{
                        Intent intent = new Intent(holder.getConvertView().getContext(), ForestDetailActivity.class);
                        intent.putExtra("forest", data);
                        startActivity(intent);
                    });
                }

            });
            recyclerView.addItemDecoration(new DividerItemDecoration(
                    DividerItemDecoration.VERTICAL_LIST,
                    ContextCompat.getColor(getActivity(), R.color.background),
                    DispalyUtil.dp2px(getActivity(), 3),
                    0, 0, false
            ));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new CommonAdapter<Forest>(getActivity(), layoutId, response) {
                @Override
                protected void convert(ViewHolder holder, Forest data, int position) {
                    ImageView image = holder.getConvertView().findViewById(R.id.image);
                    String url = ApiConstant.ROOT_URL + data.getThumbnailUrl();
                    Glide.with(getActivity()).load(url).into(image);
                    holder.setText(R.id.tvName, "姓名："+data.getAuthor());
                    holder.setText(R.id.tvBirthday, "出生日期："+data.getBirthday());
                    holder.setText(R.id.tvFunction, "职务："+data.getPartyPosts());
                    //holder.setText(R.id.tvEducation, "学历："+data.getEducation());

                    //硕士研究生、大学本科、大学专科、中等专科、普通高中、职业高中、初级中学。
                    TextView minXueli = holder.getConvertView().findViewById(R.id.tvEducation);
                    String education = data.getEducation();
                    if(education.equals("0")){
                        minXueli.setText("学历："+"硕士研究生"+ "");
                    }else if(education.equals("1")){
                        minXueli.setText("学历："+"大学本科" + "");
                    }else if(education.equals("2")){
                        minXueli.setText("学历："+"大学专科" + "");
                    }else if(education.equals("3")){
                        minXueli.setText("学历："+"中等专科" + "");
                    }else if(education.equals("4")){
                        minXueli.setText("学历："+"普通高中" + "");
                    }else if(education.equals("5")){
                        minXueli.setText("学历："+"职业高中"+ "");
                    }else if(education.equals("6")){
                        minXueli.setText("学历："+"初级中学" + "");
                    }else{
                        minXueli.setText("学历："+education + "");
                    }
                    holder.setText(R.id.tvParty, "所属支部："+data.getDeptName());
                    holder.getConvertView().setOnClickListener(detail->{
                        Intent intent = new Intent(holder.getConvertView().getContext(), ForestDetailActivity.class);
                        intent.putExtra("forest", data);
                        startActivity(intent);
                    });
                }

            });
        }

        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.background),
                DispalyUtil.dp2px(getActivity(), 3),
                0, 0, false
        ));
    }

}
