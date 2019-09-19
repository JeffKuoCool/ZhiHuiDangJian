package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
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
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.model.PartyOrganiza;
import com.lfc.zhihuidangjianapp.ui.activity.model.UiName;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-06
 * @autror: guojian
 * @description:
 */
public class Act_Party_Change extends BaseActivity {

    private RecyclerView recyclerView;

    private ImageView ivHead;

    private TextView tvSubmit;

    private EditText etParyName, etReason;

    private String[] titles = {"姓名", "性别", "民族", "出生日期", "学历", "支部"};

    private List<UiName> uiNameList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_party_change;
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
        ivHead = findViewById(R.id.iv_head);
        tvSubmit = findViewById(R.id.tv_submit);
        etParyName = findViewById(R.id.et_party_name);
        etReason = findViewById(R.id.et_reason);

        setEvent();
    }

    private void setEvent() {
        tvSubmit.setOnClickListener(submit->{
            if(etParyName.getText().toString().trim().isEmpty()){
                showTextToast("请填写申请转移组织的名称");
                return;
            }
            if(etReason.getText().toString().trim().isEmpty()){
                showTextToast("请填写转移组织的原因");
                return;
            }
            submit();
        });
    }

    /**
     * 申请转移组织
     */
    private void submit(){
        Map<String, Object> map = new HashMap<>();
        map.put("applyDeptName", etParyName.getText().toString().trim());
        map.put("transferReason", etReason.getText().toString().trim());
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .insertTransferOrganizationalRelations(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<Object>(getActivity()) {

                    @Override
                    protected void onNext(Object response) {
                        if (response == null) return;
                        showTextToast("已申请");
                        finish();
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    /**
     * 查询是否申请转移组织
     */
    private void loadOrganizational(){
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyTransferOrganizationalRelationsDetail( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<PartyOrganiza>(getActivity()) {

                    @Override
                    protected void onNext(PartyOrganiza response) {
                        if (response == null) return;
                        Log.e("onNext= ", response.toString());
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter<UiName>(getActivity(), R.layout.item_user_info, formatData()) {

            @Override
            protected void convert(ViewHolder holder, UiName data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                holder.setText(R.id.tv_name, data.getName());
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.background),
                DispalyUtil.dp2px(getActivity(), 3),
                0, 0, false
        ));

        User user = MyApplication.getmUserInfo().getUser();
        Glide.with(this).load(ApiConstant.ROOT_URL+user.getImgAddress()).into(ivHead);

        loadOrganizational();
    }

    private List<UiName> formatData(){
        User user = MyApplication.getmUserInfo().getUser();
        uiNameList.clear();
        for (int i=0; i<titles.length;i++){
            UiName uiName = new UiName();
            uiName.setTitle(titles[i]);
            switch (i){
                case 0://姓名
                    uiName.setName(user.getSealName());
                    break;
                case 1://性别
                    uiName.setName(user.getUserNumber()+"");
                    break;
                case 2://民族
                    uiName.setName(user.getNation()+"");
                    break;
                case 3://出生日期

                    uiName.setName(DateUtils.timeStampToStr(user.getBirthday(), "yyyy-MM-dd"));
                    break;
                case 4://学历
                    uiName.setName(user.getEducation()+"");
                    break;
                case 5://支部
                    uiName.setName(user.getSubordinatePartyGroup()+"");
                    break;
            }
            uiNameList.add(uiName);
        }
        return uiNameList;
    }

}
