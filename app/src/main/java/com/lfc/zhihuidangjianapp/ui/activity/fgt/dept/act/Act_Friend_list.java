package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.event.EventConstants;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.FriendListAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.model.FriendList;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 通讯录-用户
 */
public class Act_Friend_list extends BaseActivity {

    public static final int ENTER_CLOSE = 0;
    public static final int ENTER_SIGLE = 1;
    public static final int ENTER_NORMAL = 2;

    private int enter;

    private RecyclerView recyclerView;

    private TextView tvRight;

    private LinearLayoutManager linearLayoutManager;

    private FriendListAdapter friendListAdapter;

    private String deptNumber;

    private List<User> userList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview_with_appbar;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        findViewById(R.id.imgBack).setOnClickListener(back -> finish());
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("通讯录");
        recyclerView = findViewById(R.id.recyclerView);
        tvRight = findViewById(R.id.tvRight);
        RxBus.get().register(this);
    }

    private void setEvent() {
        friendListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(enter==ENTER_NORMAL){
                    //多选模式为邀请成员参加会议，禁止响应
                    return;
                }
                User user = userList.get(position);
                Intent intent = new Intent(Act_Friend_list.this, MailDetailActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        deptNumber = getIntent().getStringExtra("deptNumber");
        enter = getIntent().getIntExtra("enter", 0);
        Map<String, Object> map = new HashMap<>();
        map.put("deptNumber", deptNumber);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryUserListByFirstPinYin(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<FriendList>(getActivity()) {

                    @Override
                    protected void onNext(FriendList response) {
                        if (response == null) return;
                        Log.e("onNext= ", response.toString());
                        userList = response.getUserList();
                        if (enter == ENTER_NORMAL) {
                            tvRight.setVisibility(View.VISIBLE);
                            tvRight.setText("邀请");
                            tvRight.setOnClickListener(users -> {
                                List<User> selectUsers = getSelectUser();
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < selectUsers.size(); i++) {
                                    if (i == 0) {
                                        stringBuilder.append(selectUsers.get(i).getLoginName());
                                    } else {
                                        stringBuilder.append("," + selectUsers.get(i).getLoginName());
                                    }
                                }

                                Intent intent = new Intent();
                                intent.putExtra("users", stringBuilder.toString());
                                setResult(EventConstants.EVENT_APPLY, intent);
                                finish();
                            });
                        }
                        setRecyclerView(response);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    private void setRecyclerView(FriendList response) {
        //TODO 初始化操作
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        friendListAdapter = new FriendListAdapter(MyApplication.getAppContext(), R.layout.item_friend_list, response.getUserList());
        friendListAdapter.setEnter(enter);
        recyclerView.setAdapter(friendListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.background),
                DispalyUtil.dp2px(getActivity(), 3),
                0, 0, false
        ));
        setEvent();
    }

    private List<User> getSelectUser() {
        if (friendListAdapter == null) {
            return new ArrayList<>();
        }
        return friendListAdapter.getSelectUser();
    }

}
