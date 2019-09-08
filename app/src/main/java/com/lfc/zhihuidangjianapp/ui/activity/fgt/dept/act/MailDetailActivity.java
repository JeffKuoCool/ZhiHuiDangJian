package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.ui.activity.chat.ChatActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.AppConferenceActivity;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;

import java.util.ArrayList;

/**
 * @date: 2019-09-05
 * @autror: guojian
 * @description: 即时通讯
 */
public class MailDetailActivity extends BaseActivity {

    private User user;

    private TextView tvName, tvLoginName, tvMobile, tvDept;

    private ImageView ivHead;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mail_detail;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        initImmersionBar(0);
        tvName = findViewById(R.id.tvName);
        tvLoginName = findViewById(R.id.tvLoginName);
        tvMobile = findViewById(R.id.tvMobile);
        tvDept = findViewById(R.id.tvDept);
        ivHead = findViewById(R.id.ivHead);
        user = (User)getIntent().getParcelableExtra("user");
        if(user==null){
            return;
        }
        Glide.with(this).load(ApiConstant.ROOT_URL+user.getImgAddress()).into(ivHead);
        tvName.setText("姓名："+user.getSealName());
        tvLoginName.setText("账号："+user.getLoginName());
        tvMobile.setText("电话号码："+user.getMobileNumber());
        tvDept.setText("所属支部："+user.getDeptName());
        findViewById(R.id.tvCall).setOnClickListener(call->{
            //TODO 音视频语音
            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            Intent intent = new Intent(getActivity(), AppConferenceActivity.class);
            intent.putParcelableArrayListExtra("users", users);
            startActivity(intent);
        });
        findViewById(R.id.tvChat).setOnClickListener(call->{
            //TODO 私信
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("user", user.getLoginName());
            startActivity(intent);
        });

    }

    @Override
    protected void initData() {

    }
}
