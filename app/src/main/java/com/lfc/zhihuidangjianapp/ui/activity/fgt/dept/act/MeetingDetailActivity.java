package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.ui.activity.chat.ChatActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.AppConferenceActivity;
import com.lfc.zhihuidangjianapp.ui.activity.model.Meeting;

/**
 * @date: 2019-08-28
 * @autror: guojian
 * @description: 会议详情
 */
public class MeetingDetailActivity extends BaseActivity {

    private TextView tvTitle, tvTheme, tvTime, tvStartTime, tvCreateName, tvJoinMember, tvContent;

    private Meeting meeting;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_detail;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        meeting = (Meeting)getIntent().getSerializableExtra("Meeting");
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        tvTitle = findViewById(R.id.tv_title);
        tvTheme = findViewById(R.id.tv_theme);
        tvTime = findViewById(R.id.tv_time);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvCreateName = findViewById(R.id.tv_create_name);
        tvJoinMember = findViewById(R.id.tv_join_member);
        tvContent = findViewById(R.id.tv_content);

        if(meeting==null)return;
        tvTitle.setText("会议主题："+meeting.getTitle());
        tvTheme.setText("会议日期："+meeting.getMeetingDate());
        tvTime.setText("会议准备时间："+meeting.getReadyTime());
        tvStartTime.setText("会议开始时间："+meeting.getStartTime());
        tvCreateName.setText("发起人："+meeting.getSendPerson());
        tvJoinMember.setText("参会人员："+meeting.getUsers());
        tvContent.setText("会议内容："+meeting.getIntroduction());

        findViewById(R.id.tv_detail).setOnClickListener(detail->{
            //进入会议
            if(meeting==null)return;
            Intent intent = new Intent(getActivity(), AppConferenceActivity.class);
            intent.putExtra("Meeting", meeting);
            intent.putExtra("enterType", AppConferenceActivity.SUBSCRIBE);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {

    }
}
