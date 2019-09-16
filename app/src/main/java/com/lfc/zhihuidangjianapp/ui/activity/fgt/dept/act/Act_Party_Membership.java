package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.lfc.zhihuidangjianapp.ui.activity.model.DeptDetailUser;
import com.lfc.zhihuidangjianapp.ui.activity.model.PartyOrganiza;
import com.lfc.zhihuidangjianapp.ui.activity.model.PartyPayment;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponsePartyPayment;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-08
 * @autror: guojian
 * @description: 党费明细
 */
public class Act_Party_Membership extends BaseActivity {

    private RecyclerView recyclerView;

    private TextView tvPartyShip, tvName;

    private View viewPartyShip;

    private ImageView ivHead;

    private ResponsePartyPayment responsePartyPayment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_party_membership;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        initImmersionBar(0);
        recyclerView = findViewById(R.id.recyclerView);
        tvPartyShip = findViewById(R.id.tv_party_ship);
        viewPartyShip = findViewById(R.id.view_party_ship);
        ivHead = findViewById(R.id.iv_head);
        tvName = findViewById(R.id.tv_name);

        setEvent();
    }

    private void setEvent() {
        viewPartyShip.setOnClickListener(partyShip->{
            if(responsePartyPayment==null)return;
            Intent intent = new Intent(this, Act_Party_Pay.class);
            intent.putExtra("pay", responsePartyPayment.getMoney());
            intent.putExtra("partyPaymentHisId", responsePartyPayment.getPartyPaymentHisId()+"");
            startActivity(intent);

        });
        //我的组织
        findViewById(R.id.tvRight).setOnClickListener(myOrgnize->{
            startActivity(new Intent(this, MyOrgnizeActivity.class));
        });
    }

    private void initPayView(ResponsePartyPayment response){
        tvPartyShip.setText("需缴纳党费"+response.getMoney()+"元");
        Glide.with(this).load(ApiConstant.ROOT_URL+MyApplication.getmUserInfo().getUser().getImgAddress()).into(ivHead);
        tvName.setText(response.getUserName());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter<PartyPayment>(getActivity(), R.layout.item_party_membership, response.getPartyPaymentHisList().getDatas()) {
            @Override
            protected void convert(ViewHolder holder, PartyPayment data, int position) {
                holder.setText(R.id.tv_ship, data.getPayYearMonth()+"已缴党费"+data.getMoney()+"元");
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyPartyPaymentHisPageList( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponsePartyPayment>(getActivity()) {

                    @Override
                    protected void onNext(ResponsePartyPayment response) {
                        if (response == null) return;
                        Log.e("onNext= ", response.toString());
                        responsePartyPayment = response;
                        initPayView(response);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    @Override
    protected void initData() {
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyPartyPaymentHisPageList( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponsePartyPayment>(getActivity()) {

                    @Override
                    protected void onNext(ResponsePartyPayment response) {
                        if (response == null) return;
                        Log.e("onNext= ", response.toString());
                        responsePartyPayment = response;
                        initPayView(response);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
