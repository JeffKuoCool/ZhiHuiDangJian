package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.chat.EazyChatApi;
import com.lfc.zhihuidangjianapp.databinding.WindowMeetingCenterBinding;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.chat.ChatActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.MeetingDetailActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.AppConferenceActivity;
import com.lfc.zhihuidangjianapp.ui.activity.model.BaseResponse;
import com.lfc.zhihuidangjianapp.ui.activity.model.Meeting;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseMeetingMine;
import com.lfc.zhihuidangjianapp.widget.CustomPopWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-22
 * @autror: guojian
 * @description:
 */
public class Fgt_Meeting_Center extends BaseFragment {

    private int type;

    private RecyclerView recyclerView;

    private WindowMeetingCenterBinding windowCommenBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        windowCommenBinding = DataBindingUtil.inflate(inflater, R.layout.window_meeting_center, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

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
        String username = MyApplication.getmUserInfo().getUser().getLoginName();
        String pwd = MyApplication.getmUserInfo().getUser().getImPwd();
        //登录环信
        EazyChatApi.loginChat(username, pwd, (BaseActivity) getActivity(), null);

        type = getArguments().getInt("type");
        Observable<BaseResponse<ResponseMeetingMine>> observable;
        if(type == 0){
            observable = RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .queryMyMeetingPageList( MyApplication.getLoginBean().getToken());
        }else if(type==1){
            observable = RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .queryMeetingPageList( MyApplication.getLoginBean().getToken());
        }else if(type==2){
            observable = RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .queryMeetingHisPageList( MyApplication.getLoginBean().getToken());
        }else{
            observable = RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .queryMeetingPageList( MyApplication.getLoginBean().getToken());
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponseMeetingMine>(getActivity()) {

                    @Override
                    protected void onNext(ResponseMeetingMine response) {
                        if (response == null) return;
                        Log.e("onNext= ", response.toString());
                        setRecyclerView(response.getMeetingList().getDatas());
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());

    }

    private void setRecyclerView(List<Meeting> response) {
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<Meeting>(MyApplication.getAppContext(), R.layout.item_meeting_center, response) {
            @Override
            protected void convert(ViewHolder holder, Meeting data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                holder.setText(R.id.tv_start_time, "会议开始时间："+data.getStartTime());
                holder.setText(R.id.tv_create_name, "会议创建人："+data.getSendPerson());
//                if(TextUtils.isEmpty(data.getConfrId())&&!data.getCreateCode().equals(MyApplication.getmUserInfo().getUser().getLoginName())){
//                    //会议室不存在，不是创建人无权限进入会议
//                    return;
//                }
                View convertView =  holder.getConvertView();
                TextView tvJoinMeeting = convertView.findViewById(R.id.tv_join_meeting);
                tvJoinMeeting.setOnClickListener(confe->{
                    if(data.getUsers()==null){
                        return;
                    }
                    //进入会议
                    Log.e("join_meeting", "");
                    if(!data.getCreateCode().equals(MyApplication.getmUserInfo().getUser().getLoginName())&&
                            !data.getUsers().contains(MyApplication.getmUserInfo().getUser().getLoginName())){
                        verify(data);
                    }else{
                        enter(data);
                    }
                });
                holder.getConvertView().findViewById(R.id.tv_meeting_detail).setOnClickListener(confe->{
                    //会议详情
                    Intent intent = new Intent(getActivity(), MeetingDetailActivity.class);
                    intent.putExtra("Meeting", data);
                    startActivity(intent);
                });
            }

        });
    }
    /**
     * 非邀请进入验证密码
     */
    private void verify( Meeting data){
        CustomPopWindow popWindow = new CustomPopWindow.Builder(getContext())
                .setwidth(LinearLayout.LayoutParams.MATCH_PARENT)
                .setheight(LinearLayout.LayoutParams.WRAP_CONTENT)
                .setContentView(windowCommenBinding.getRoot())
                .setFouse(true)
                .setOutSideCancel(true)
                .setBgAlpha(0.5f)
                .builder()
                .showAtLocation(R.layout.parent_recyclerview, Gravity.CENTER, 0, 0);
        windowCommenBinding.btnRight.setOnClickListener(submit->{
            if(windowCommenBinding.tvDec.getText().toString().trim().equals(data.getPassword())){
                enter(data);
            }else{
                toast("密码错误");
            }
            popWindow.dismiss();
        });
        windowCommenBinding.btnLeft.setOnClickListener(cancel->popWindow.dismiss());
    }

    /**
     * 进入会议
     * @param data
     */
    private void enter(Meeting data){
        Intent intent = new Intent(getActivity(), AppConferenceActivity.class);
        intent.putExtra("Meeting", data);
        intent.putExtra("enterType", AppConferenceActivity.SUBSCRIBE);
        startActivity(intent);
    }


}
