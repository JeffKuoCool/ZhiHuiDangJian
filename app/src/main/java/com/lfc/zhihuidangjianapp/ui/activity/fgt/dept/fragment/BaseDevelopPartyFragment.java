package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.model.DevelopParty;
import com.lfc.zhihuidangjianapp.ui.activity.model.NativeDevelopParty;
import com.lfc.zhihuidangjianapp.ui.activity.model.Payment;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
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
 * @date: 2019-09-02
 * @autror: guojian
 * @description:
 */
public abstract class BaseDevelopPartyFragment extends BaseFragment {

    protected List<NativeDevelopParty> parties = new ArrayList<>();

    private RecyclerView recyclerView;

    protected CommonAdapter mAdapter;

    public Map<String, Object> params = new HashMap<>();

    protected View mRootView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_develop;
    }

    @Override
    protected void initView(View rootView) {
        mRootView = rootView;
        recyclerView = rootView.findViewById(R.id.recyclerView);
        rootView.findViewById(R.id.tvSave).setOnClickListener(save->{
            for (NativeDevelopParty developParty: parties){
                if(TextUtils.isEmpty(developParty.getContent())){
                    toast("请填写"+developParty.getTitle());
                    return;
                }
            }
            submit(parties);
        });
    }

    @Override
    protected void initData() {
        parties = getParties();
        String json = new Gson().toJson(parties);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.background),
                DispalyUtil.dp2px(getActivity(), 3),
                0, 0, false
        ));
        setRecyclerView();
    }

    public void setRecyclerView(){
        mAdapter = new CommonAdapter<NativeDevelopParty>(getActivity(), R.layout.item_develop_party, parties) {
            @Override
            protected void convert(ViewHolder holder, NativeDevelopParty data, int position) {
                if(data.getStyleId() == 0){
                    holder.getConvertView().findViewById(R.id.input).setVisibility(View.GONE);
                    TextView tvContent = holder.getConvertView().findViewById(R.id.tvContent);
                    tvContent.setVisibility(View.VISIBLE);
                    if(TextUtils.isEmpty(data.getContent())){
                        tvContent.setText("请选择"+data.getTitle());
                    }else{
                        tvContent.setText(data.getContent());
                    }
                    if(data.status==0) {
                        tvContent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //选择时间
                                selectTime(position, tvContent);
                            }
                        });
                    }
                }else{
                    EditText input = holder.getConvertView().findViewById(R.id.input);
                    if(TextUtils.isEmpty(data.getContent())){
                        input.setHint("请输入"+data.getTitle());
                    }else{
                        input.setText(data.getContent());
                    }
                    input.setVisibility(View.VISIBLE);
                    holder.getConvertView().findViewById(R.id.tvContent).setVisibility(View.GONE);
                    input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            parties.get(position).setContent(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                holder.setText(R.id.tvTitle, data.getTitle());
            }

        };
        recyclerView.setAdapter(mAdapter);
    }

    private void selectTime(final int position, TextView text) {
        final DatePicker picker = new DatePicker(getActivity());
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(getActivity(), 10));
        picker.setRangeEnd(2050, 1, 11);
        picker.setRangeStart(2016, 8, 29);
        picker.setSelectedItem(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String time = year+"-"+month+"-"+day+"";
                text.setText(time);
                parties.get(position).setContent(time);
            }
        });
        picker.show();
    }

    public abstract List<NativeDevelopParty> getParties();

    public abstract void submit(List<NativeDevelopParty> parties);

    protected void saveData(Map<String, Object> map){
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .insertJoinPartyStage(params, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<Object>(getActivity()) {

                    @Override
                    protected void onNext(Object response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null) return;
                        toast("保存成功");
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }
                .actual());
    }

}
