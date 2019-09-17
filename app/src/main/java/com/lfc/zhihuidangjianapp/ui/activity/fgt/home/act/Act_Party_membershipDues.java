package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Pay;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.bean.Party_membershipDuesBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 党费缴纳
 */
public class Act_Party_membershipDues extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.My_organization)
    TextView MyOrganization;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_guanli)
    TextView profile_guanli;
    @BindView(R.id.tv_pay_party)
    TextView tvPayParty;

    @Override
    protected int getLayoutId() {
        return R.layout.act_party_membership_dues;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        findViewById(R.id.imgBack).setOnClickListener(this);
        Glide.with(getContext()).load(ApiConstant.ROOT_URL + MyApplication.getLoginBean().getContextUrl()).into(profileImage);
        profileName.setText(MyApplication.getLoginBean().getRealName());
        profile_guanli.setText(MyApplication.getLoginBean().getRoleName());
        setEvent();
    }

    private void setEvent() {
        tvPayParty.setOnClickListener(pay -> {
            startActivity(new Intent(getContext(), Act_Party_Pay.class));
            finish();
        });
    }

    @Override
    protected void initData() {
//        queryMyPartyPaymentHisPageList();
        queryPartyPaymentHisPageList();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }

    /**
     * 我的党费
     */
    public void queryMyPartyPaymentHisPageList() {
        int pageNum = 1;
        HttpHelper.queryMyPartyPaymentHisPageList(pageNum + "", new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
            }

            @Override
            public void onSucceed(String succeed) {
                loding.dismiss();
                Log.e("aa", "----------------" + MyApplication.getLoginBean().getToken());
                Gson gson = new Gson();
                Party_membershipDuesBean entity = gson.fromJson(succeed, Party_membershipDuesBean.class);
                if (entity.getCode() == 0) {
                    profileName.setText(entity.getData().getUserName());
                }
                Log.e("aa", "----------------" + entity.getData().getPartyPaymentHisList().getDatas().size());
            }

            @Override
            public void onError(String error) {
                loding.dismiss();
                ToastUtils.show(error);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 分页查询党费缴费记录信息-已缴列表
     */
    public void queryPartyPaymentHisPageList() {
        HttpHelper.queryPartyPaymentHisPageList(new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
            }

            @Override
            public void onSucceed(String succeed) {
                loding.dismiss();
//                Log.e("aa", "----------------" + MyApplication.getLoginBean().getToken());
//                Gson gson = new Gson();
//                Party_membershipDuesBean entity = gson.fromJson(succeed, Party_membershipDuesBean.class);
//                if (entity.getCode() == 0) {
//                    profileName.setText(entity.getData().getUserName());
//                }
//                Log.e("aa", "----------------" + entity.getData().getPartyPaymentHisList().getDatas().size());
            }

            @Override
            public void onError(String error) {
                loding.dismiss();
                ToastUtils.show(error);
            }
        });
    }
}
