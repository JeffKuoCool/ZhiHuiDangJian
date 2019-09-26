package com.lfc.zhihuidangjianapp.ui.activity.fgt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.ui.MainActivity;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.app.UserConstants;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.Act_Login;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Membership;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Weekend_Report;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.Act_Integral;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.Act_SetUpc;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.CodeSafeActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.UserDataBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseWorkReport;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;
import com.lfc.zhihuidangjianapp.utlis.DialogUtils;
import com.lfc.zhihuidangjianapp.utlis.SPUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人中心
 */
public class Fgt_Personal extends BaseFragment {


    @BindView(R.id.min_left)
    ImageView minLeft;
    @BindView(R.id.min_hader)
    ImageView minHader;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.min_Total)
    TextView minTotal;
    @BindView(R.id.min_Total_lin)
    LinearLayout minTotalLin;
    @BindView(R.id.min_starPartyMember)
    TextView minStarPartyMember;
    @BindView(R.id.min_starPartyMember_lin)
    LinearLayout minStarPartyMemberLin;
    @BindView(R.id.min_name)
    TextView minName;
    @BindView(R.id.min_sex)
    TextView minSex;
    @BindView(R.id.min_minzu)
    TextView minMinzu;
    @BindView(R.id.min_xueli)
    TextView minXueli;
    @BindView(R.id.min_shenri)
    TextView minShenri;
    @BindView(R.id.min_yuanxiao)
    TextView minYuanxiao;
    @BindView(R.id.min_zhuanye)
    TextView minZhuanye;
    @BindView(R.id.min_zhibu)
    TextView minZhibu;
    @BindView(R.id.min_zhiwu)
    TextView minZhiwu;
    @BindView(R.id.min_canjai)
    TextView minCanjai;
    @BindView(R.id.min_jiaru)
    TextView minJiaru;
    @BindView(R.id.min_chengwei)
    TextView minChengwei;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.min_naem1)
    TextView minNaem1;

    @BindView(R.id.min_code)
    TextView minCode;
    @BindView(R.id.min_Total1)
    TextView minTotal1;
    @BindView(R.id.min_shezhi)
    RelativeLayout minShezhi;
    @BindView(R.id.min_zhanghuanquan)
    RelativeLayout minZhanghuanquan;
    @BindView(R.id.min_gongzuozhoubao)
    RelativeLayout minGongzuozhoubao;
    @BindView(R.id.min_dangfei)
    RelativeLayout minDangfei;
    @BindView(R.id.lefit)
    LinearLayout lefit;
    @BindView(R.id.min_chengnuo)
    TextView min_chengnuo;
    @BindView(R.id.min_shifangang1)
    TextView min_shifangang1;
    @BindView(R.id.min_shifangang2)
    TextView min_shifangang2;
    @BindView(R.id.min_shifangang3)
    TextView min_shifangang3;
    @BindView(R.id.min_shifangang0)
    TextView min_shifangang0;
    @BindView(R.id.min_shifangangname)
    TextView min_shifangangname;
    @BindView(R.id.drawlayout)
    DrawerLayout drawerLayout;
    Unbinder unbinder;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


    }
   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    protected void initData() {
        queryJoinPartyStageDeatil();
    }

    @Override
    public void onResume() {
        super.onResume();
        queryJoinPartyStageDeatil();
    }

    /**
     * 查询发展党员信息
     */
    public void queryJoinPartyStageDeatil() {
        HttpHelper.queryJoinPartyStageDeatil(new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
            }

            @Override
            public void onSucceed(String succeed) {
                loding.dismiss();
                Gson gson = new Gson();
                UserDataBean entity = gson.fromJson(succeed, UserDataBean.class);
                if (entity.getCode() == 0) {
                    RequestOptions options = new RequestOptions();
                    options.placeholder(R.mipmap.iocn_moren);
                    options.error(R.mipmap.iocn_moren);
                    Glide.with(getContext()).load(ApiConstant.ROOT_URL + entity.getData().getUser().getImgAddress()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(minHader);//标准圆形图片。
                    Glide.with(getContext()).load(ApiConstant.ROOT_URL + entity.getData().getUser().getImgAddress()).apply(options).into(profileImage);
                    minName.setText(entity.getData().getUser().getDisplayName());
                    minNaem1.setText(entity.getData().getUser().getDisplayName());
                    minCode.setText("账号："+entity.getData().getUser().getLoginName());
                    if (entity.getData().getUser().getSex() == 2) {
                        minSex.setText("女");
                    } else {
                        minSex.setText("男");
                    }
                    minMinzu.setText(entity.getData().getUser().getNation());
                    minXueli.setText(entity.getData().getUser().getEducation() + "");
                    minShenri.setText(stampToDate(entity.getData().getUser().getBirthday()));
                    minYuanxiao.setText(entity.getData().getUser().getGraduateSchool() + "");
                    minZhuanye.setText(entity.getData().getUser().getMajorStudied() + "");
                    minZhibu.setText(entity.getData().getUser().getDeptName() + "");
                    minZhiwu.setText(entity.getData().getUser().getPartyPosts() + "");
                    minCanjai.setText(entity.getData().getUser().getJoinWorkTime() + "");
//                    minJiaru.setText(DateUtils.timeStampToStr(entity.getData().getUser().getJoinPartyTime(), "yyyy-MM-dd"));
//                    minChengwei.setText(DateUtils.timeStampToStr(entity.getData().getUser().getToFormalPartyTime(), "yyyy-MM-dd"));
                    minJiaru.setText(entity.getData().getUser().getJoinPartyTime());
                    minChengwei.setText(entity.getData().getUser().getToFormalPartyTime());
                    minTotal.setText(entity.getData().getTotal() + "积分");
                    minStarPartyMember.setText(entity.getData().getUser().getStarPartyMember() + "星党员");
                    minTotal1.setText(entity.getData().getUser().getStarPartyMember() + "积分");
                    //partyPromise党员承诺
                    min_chengnuo.setText(entity.getData().getUser().getPartyPromise()+"");
                    //partyPromise党员示范岗
                    min_shifangangname.setText("姓名："+entity.getData().getUser().getSealName());
                    min_shifangang0.setText("所在支部："+entity.getData().getUser().getDeptName());
                    min_shifangang1.setText("党内职务："+entity.getData().getUser().getPartyPosts());
                    min_shifangang2.setText("目标职责："+entity.getData().getUser().getArea());
                    min_shifangang3.setText("示范岗："+entity.getData().getUser().getPosts());
                }
            }

            @Override
            public void onError(String error) {
                loding.dismiss();
                ToastUtils.show(error);
            }
        });
    }
    /*
     * 将时间戳转换为时间
     */
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }


    //左边菜单开关事件
    public void openLeftLayout() {
        if (drawerLayout.isDrawerOpen(lefit)) {
            drawerLayout.closeDrawer(lefit);
        } else {
            drawerLayout.openDrawer(lefit);
        }
    }


    @OnClick({R.id.min_left, R.id.min_Total_lin, R.id.min_starPartyMember_lin, R.id.min_shezhi, R.id.min_zhanghuanquan, R.id.min_gongzuozhoubao, R.id.min_dangfei, R.id.lefit, R.id.drawlayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.min_left:
                openLeftLayout();
                break;
            case R.id.min_Total_lin://积分页面
                startActivity(new Intent(getActivity(), Act_Integral.class));

                break;
            case R.id.min_starPartyMember_lin:
                //toast("星星");
                break;
            case R.id.min_shezhi:
                startActivity(new Intent(getActivity(), Act_SetUpc.class));
                break;
            case R.id.min_zhanghuanquan://账户安全
                startActivity(new Intent(getActivity(), CodeSafeActivity.class));

                break;
            case R.id.min_gongzuozhoubao://退出登录

                new DialogUtils(getActivity(), "退出登录", "确定要退出登录吗？", "取消", "确定") {
                    @Override
                    public void doClickLeft() {
                        //Toast.makeText(getPageContext(), "取消", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void doClickRight() {
                        //退出登录
                        getRetreat();
                    }

                };
                break;
            case R.id.min_dangfei://党费缴纳
                startActivity(new Intent(getActivity(), Act_Party_Membership.class));
                break;
            case R.id.lefit:
                break;
            case R.id.drawlayout:
                break;
        }
    }

    //退出登录
    private void getRetreat() {
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .logout( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<Object>(getActivity()) {
                    @Override
                    protected void onNext(Object response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                        SPUtil.remove(getActivity(), UserConstants.AUTHORIZATION);
                        EMClient.getInstance().logout(true, null);
                        startActivity(new Intent(getActivity(), Act_Login.class));
                        getActivity().finish();
                        toast("退出成功");


                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
