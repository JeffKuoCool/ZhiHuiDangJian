package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.event.BusEvent;
import com.lfc.zhihuidangjianapp.event.EventConstants;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.model.UiName;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;
import com.lfc.zhihuidangjianapp.ui.activity.model.UserInfo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-21
 * @autror: guojian
 * @description: 创建会议
 */
public class Act_Create_Meeting extends BaseActivity {

    private RecyclerView recyclerView;

    private List<UiName> uiNameList = loadData();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_meeting;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        findViewById(R.id.imgBack).setOnClickListener(back -> finish());
        recyclerView = findViewById(R.id.recyclerView);
        setRecyclerView(uiNameList);
        setEvent();
    }

    private void setEvent() {
        findViewById(R.id.view_create).setOnClickListener(create -> {
            createMeeting();
        });
    }

    @Override
    protected void initData() {

    }

    @Subscribe
    public void receiveEvent(BusEvent rxBusEvent) {
        if (rxBusEvent.getEvent() == EventConstants.EVENT_APPLY) {
            List<User> selectuser = (List<User>) rxBusEvent.getEventObj();
            if (selectuser == null || selectuser.isEmpty()) return;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < selectuser.size(); i++) {
                if (i == 0) {
                    stringBuilder.append(selectuser.get(i).getLoginName());
                } else {
                    stringBuilder.append("," + selectuser.get(i).getLoginName());
                }
            }
            uiNameList.get(5).setText(stringBuilder.toString());
            setRecyclerView(uiNameList);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            try {
                String users = data.getStringExtra("users");
                uiNameList.get(5).setText(users);
                setRecyclerView(uiNameList);
            }catch (Exception e){}
        }
    }

    private List<UiName> loadData() {
        List<UiName> uiNames = new ArrayList<>();
        uiNames.add(new UiName("会议主题：", "请输入会议主题"));
        uiNames.add(new UiName("会议日期：", "请选择会议日期"));
        uiNames.add(new UiName("会议准备时间：", "请选择会议准备时间"));
        uiNames.add(new UiName("会议开始时间：", "请选择会议开始时间"));
        uiNames.add(new UiName("发起人：", "请输入发起人"));
        uiNames.add(new UiName("参会人员：", "请选择参会人员"));
        uiNames.add(new UiName("入会密码：", "请输入入会密码"));
        uiNames.add(new UiName("会议内容：", "请输入会议内容"));
        return uiNames;
    }

    private void setRecyclerView(List<UiName> response) {
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<UiName>(MyApplication.getAppContext(), R.layout.item_create_meeting, response) {
            @Override
            protected void convert(ViewHolder holder, UiName data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                EditText edit = holder.getConvertView().findViewById(R.id.edit);
                TextView text = holder.getConvertView().findViewById(R.id.text);
                if (position == 1 || position == 2 || position == 3) {
                    text.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                    text.setText(TextUtils.isEmpty(data.getText())?data.getName():data.getText());
                    text.setOnClickListener(view -> {
                        selectTime(position, text);
                    });
                }
                else if (position == 5) {
                    text.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                    text.setText(TextUtils.isEmpty(data.getText())?data.getName():data.getText());
                    text.setOnClickListener(view -> {
                        //通讯录-支部
                        Intent intent = new Intent(getActivity(), Act_Mail_list.class);
                        intent.putExtra("enter", Act_Friend_list.ENTER_NORMAL);
                        startActivityForResult(intent, 1);
                    });
                } else {
                    edit.setVisibility(View.VISIBLE);
                    text.setVisibility(View.GONE);
                    edit.setHint(TextUtils.isEmpty(data.getText())?data.getName():data.getText());
                    edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            uiNameList.get(position).setText(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }

        });
    }


    private void selectTime(final int position, TextView text) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2050, 1, 11);
        picker.setRangeStart(2016, 8, 29);
        picker.setSelectedItem(2019, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String time = year + "-" + month + "-" + day + " 00:00:00";
                text.setText(time);
                uiNameList.get(position).setText(time);
            }
        });
        picker.show();
    }

    /**
     * 创建会议
     */
    private void createMeeting() {
        for (UiName uiName : uiNameList) {
            if (TextUtils.isEmpty(uiName.getText())) {
                showTextToast("请完善信息");
                return;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("title", uiNameList.get(0).getText());
        map.put("meetingDate", uiNameList.get(1).getText());
        map.put("readyTime", uiNameList.get(2).getText());
        map.put("startTime", uiNameList.get(3).getText());
        map.put("sendPerson", uiNameList.get(4).getText());
        map.put("users", uiNameList.get(5).getText());
        map.put("password", uiNameList.get(6).getText());
        map.put("introduction", uiNameList.get(7).getText());
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .insertMeeting(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<UserInfo>(getActivity()) {

                    @Override
                    protected void onNext(UserInfo response) {
                        Log.e("onNext= ", response.toString());
                        showTextToast("发布成功");
                        finish();
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

}
