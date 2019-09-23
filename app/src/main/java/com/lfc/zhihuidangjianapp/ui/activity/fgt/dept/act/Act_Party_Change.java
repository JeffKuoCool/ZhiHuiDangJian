package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.ChangeBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.PopBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.QueryPopBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.QueryPopRyBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Weekend_Query;
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
    private LinearLayout lin,type1,type2;

    private ImageView ivHead;

    private TextView tvSubmit,et_party_type_lin;

    private EditText etParyName, etReason,et_party_type,tv_dw_et,tv_zz_et,tv_zb_et,et_party_zhangtai;

    private String[] titles = {"姓名", "性别", "民族", "出生日期", "学历", "支部"};

    private List<UiName> uiNameList = new ArrayList<>();
    private popupWindows_popType popupWindows_popType;
    private  ListView listViewType;
    private String type;// //转移类型(0:系统外转入1:系统内转出2:系统内转移)
    private PopupWindows_pop popupWindows_pop;
    private ListView listView;
    private String deptNumberdw = "";//党委deptNumber
    private String deptNumberzz = "";//总支deptNumber
    private String deptNumberzb = "";//支部deptNumber
    private String deptNumberzb_name ="";
    private int status;
    private int transferOrganizationalRelationsId;

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
        et_party_type = findViewById(R.id.et_party_type);
        et_party_type_lin = findViewById(R.id.et_party_type_lin);
        lin =findViewById(R.id.lin);
        type1 =findViewById(R.id.type1);
        type2 =findViewById(R.id.type2);
        tv_dw_et =findViewById(R.id.tv_dw_et);
        tv_zz_et =findViewById(R.id.tv_zz_et);
        tv_zb_et =findViewById(R.id.tv_zb_et);
        et_party_zhangtai =findViewById(R.id.et_party_zhangtai);
        loadOrganizational();
        setEvent();
    }

    private void setEvent() {
        Log.i("yy--updateTransfer",status+"=="+type);
        tvSubmit.setOnClickListener(submit->{
            //status ==4 是修改

           if(status==4){
               if(type.equals("1")){
                   if(etParyName.getText().toString().trim().isEmpty()){
                       showTextToast("请填写申请转移组织的名称");
                       return;
                   }
                   if(etReason.getText().toString().trim().isEmpty()){
                       showTextToast("请填写转移组织的原因");
                       return;
                   }
                   Log.i("yy--submit",etParyName.getText().toString().trim()+"=="+etReason.getText().toString().trim()+"==="+deptNumberdw+"=="+deptNumberzz+"=="+deptNumberzb);
                   updata_submit(etParyName.getText().toString().trim(),etReason.getText().toString().trim());
               }else{
                   if(deptNumberzb_name.equals("")){
                       showTextToast("请选择关系转移");
                       return;
                   }
                   if(etReason.getText().toString().trim().isEmpty()){
                       showTextToast("请填写转移组织的原因");
                       return;
                   }
                   Log.i("yy--submit",etParyName.getText().toString().trim()+"=="+etReason.getText().toString().trim()+"==="+deptNumberdw+"=="+deptNumberzz+"=="+deptNumberzb);
                   updata_submit(deptNumberzb_name,etReason.getText().toString().trim());
               }
           }else{
               if(type.equals("1")){
                   if(etParyName.getText().toString().trim().isEmpty()){
                       showTextToast("请填写申请转移组织的名称");
                       return;
                   }
                   if(etReason.getText().toString().trim().isEmpty()){
                       showTextToast("请填写转移组织的原因");
                       return;
                   }
                   Log.i("yy--submit",etParyName.getText().toString().trim()+"=="+etReason.getText().toString().trim()+"==="+deptNumberdw+"=="+deptNumberzz+"=="+deptNumberzb);
                   submit(etParyName.getText().toString().trim(),etReason.getText().toString().trim());
               }else{
                   if(deptNumberzb_name.equals("")){
                       showTextToast("请选择关系转移");
                       return;
                   }
                   if(etReason.getText().toString().trim().isEmpty()){
                       showTextToast("请填写转移组织的原因");
                       return;
                   }
                   Log.i("yy--submit",etParyName.getText().toString().trim()+"=="+etReason.getText().toString().trim()+"==="+deptNumberdw+"=="+deptNumberzz+"=="+deptNumberzb);
                   submit(deptNumberzb_name,etReason.getText().toString().trim());
               }
           }
        });
        //选择转移类型
        et_party_type_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转移类型(0:系统外转入1:系统内转出2:系统内转移)
                getPoPType();
            }
        });
        //选择党委
        tv_dw_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //type  1党委弹出  2总支弹出  3支部弹出
                getPoP(1);

            }
        });
        //选择总支
        tv_zz_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_dw_et.getText().toString().trim().equals("")) {
                    ToastUtils.show("请选择党委");
                    return;
                }
                getPoP(2);

            }
        });
        //选择支部
        tv_zb_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_zz_et.getText().toString().trim().equals("")) {
                    ToastUtils.show("请选择总支");
                    return;
                }
                getPoP(3);

            }
        });
    }
    //弹出底部窗口
    private void getPoPType() {
        popupWindows_popType = new popupWindows_popType(getActivity());
    }

    /**
     * 弹出popup 选择底部弹出
     *
     * @author lijipei
     */
    public class popupWindows_popType extends PopupWindow {
        public popupWindows_popType(Context mContext) {
            View view = View.inflate(mContext, R.layout.item_pop_query, null);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            // 设置SelectPicPopupWindow弹出窗体动画效果
            // this.setAnimationStyle(R.style.hh_window_share_anim);
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(lin, Gravity.BOTTOM, 0, 0);
            View cView = view.findViewById(R.id.item_popupwindows_view);
            TextView quxiao = (TextView) view
                    .findViewById(R.id.item_pop_quxiao);
            TextView m_pop_text = (TextView) view
                    .findViewById(R.id.m_pop_text);
            m_pop_text.setText("转移类型");
            listViewType = (ListView) view.findViewById(R.id.item_pop_list);
            //设置数据
            getData();
            cView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dismiss();
                }
            });
            quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dismiss();
                }
            });


        }
    }
    private void getData() {
        ArrayList<PopBean> list1 = new ArrayList<>();
        PopBean phBean1 = new PopBean("1", "系统内转出");
        PopBean phBean2 = new PopBean("2", "系统内转移");
        list1.add(phBean1);
         list1.add(phBean2);
        getSetBz(list1);
    }
    private void getSetBz(final List<PopBean> data) {
        final MyAdapterType adapter = new MyAdapterType(data);
        listViewType.setAdapter(adapter);
        listViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //cid =data.get(position).getCid() ;
                type = data.get(position).getId();
                String balance = data.get(position).getName();
                et_party_type.setText(balance);
                if(type.equals("1")){
                    type1.setVisibility(View.VISIBLE);
                    type2.setVisibility(View.GONE);
                    et_party_type.setText(balance);
                    deptNumberdw="";
                    deptNumberzz="";
                    deptNumberzb="";
                }else if(type.equals("2")){
                    type1.setVisibility(View.GONE);
                    type2.setVisibility(View.VISIBLE);
                    et_party_type.setText(balance);
                    etParyName.setText("");
                    etReason.setText("");
                }
                popupWindows_popType.dismiss();

            }
        });
    }

    public class MyAdapterType extends BaseAdapter {

        private List<PopBean> stuList;
        private LayoutInflater inflater;

        public MyAdapterType(List<PopBean> stuList) {
            this.stuList = stuList;
            this.inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return stuList == null ? 0 : stuList.size();
        }

        @Override
        public PopBean getItem(int position) {
            return stuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //加载布局为一个视图
            View view = inflater.inflate(R.layout.item_pop_text, null);
            TextView tv_name = (TextView) view.findViewById(R.id.text_mm);
            tv_name.setText(stuList.get(position).getName());
            return view;
        }
    }
    /**
     * 修改转移组织
     */
    private void updata_submit(String etParyName,String etReason){
        Map<String, Object> map = new HashMap<>();
        map.put("applyDeptName", etParyName);
        map.put("transferReason", etReason);
        map.put("type", type);
        map.put("transferOrganizationalRelationsId",transferOrganizationalRelationsId);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .updateTransfer(map, MyApplication.getLoginBean().getToken())
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
     * 申请转移组织
     */
    private void submit(String etParyName,String etReason){
        Map<String, Object> map = new HashMap<>();
        map.put("applyDeptName", etParyName);
        map.put("transferReason", etReason);
        map.put("type", type);
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
                .subscribe(new ResponseObserver<ChangeBean>(getActivity()) {

                    @Override
                    protected void onNext(ChangeBean response) {
                        if (response == null) return;
                        Log.e("onNext= ", response.toString());
                        transferOrganizationalRelationsId=response.getTransferOrganizationalRelations().getTransferOrganizationalRelationsId();
                        //transferOrganizationalRelationsId==0没有申请过可以直接申请
                        if(transferOrganizationalRelationsId!=0){
                            //状态(0:待转出1:待转入2:已转出3:已转入4:已驳回5:内部待转6：内部已转)
                            status = response.getTransferOrganizationalRelations().getStatus();
                            //转移类型(0:系统外转入1:系统内转出2:系统内转移)
                            int type = response.getTransferOrganizationalRelations().getType();
                            if(type==0){
                                et_party_type.setText("系统外转入");
                            } else if(type==1){
                                et_party_type.setText("系统内转出");
                            }else if(type==2){
                                et_party_type.setText("系统内转移");
                            }

                            etParyName.setText(response.getTransferOrganizationalRelations().getApplyDeptName());
                            etReason.setText(response.getTransferOrganizationalRelations().getTransferReason());
                            if(status ==0){
                                et_party_zhangtai.setText("待转出");
                                tvSubmit.setVisibility(View.GONE);
                                et_party_type_lin.setEnabled(false);
                                type1.setVisibility(View.VISIBLE);
                                etReason.setEnabled(false);
                                etParyName.setEnabled(false);

                            }else if(status ==1){
                                et_party_zhangtai.setText("待转入");
                                tvSubmit.setVisibility(View.GONE);
                                et_party_type_lin.setEnabled(false);
                                type1.setVisibility(View.VISIBLE);
                                etReason.setEnabled(false);
                                etParyName.setEnabled(false);

                            }else if(status ==2){
                                et_party_zhangtai.setText("已转出");
                                tvSubmit.setVisibility(View.GONE);
                                et_party_type_lin.setEnabled(false);
                                type1.setVisibility(View.VISIBLE);
                                etReason.setEnabled(false);
                                etParyName.setEnabled(false);

                            }else if(status ==3){
                                et_party_zhangtai.setText("已转入");
                                tvSubmit.setVisibility(View.GONE);
                                et_party_type_lin.setEnabled(false);
                                type1.setVisibility(View.VISIBLE);
                                etReason.setEnabled(false);
                                etParyName.setEnabled(false);

                            }else if(status ==4){
                                et_party_zhangtai.setText("已驳回");
                                tvSubmit.setVisibility(View.VISIBLE);
                                et_party_type_lin.setEnabled(true);
                                type1.setVisibility(View.VISIBLE);
                                etReason.setEnabled(true);
                                etParyName.setEnabled(true);

                            }else if(status ==5){
                                et_party_zhangtai.setText("内部待转");
                                tvSubmit.setVisibility(View.GONE);
                                et_party_type_lin.setEnabled(false);
                                type1.setVisibility(View.VISIBLE);
                                etReason.setEnabled(false);
                                etParyName.setEnabled(false);
                            }else if(status ==6){
                                et_party_zhangtai.setText("内部已转");
                                tvSubmit.setVisibility(View.GONE);
                                et_party_type_lin.setEnabled(false);
                                type1.setVisibility(View.VISIBLE);
                                etReason.setEnabled(false);
                                etParyName.setEnabled(false);
                            }
                        }else{
                            et_party_zhangtai.setText("待申请");
                            tvSubmit.setVisibility(View.VISIBLE);
                            et_party_type_lin.setEnabled(true);
                            type1.setVisibility(View.VISIBLE);
                            etReason.setEnabled(true);
                            etParyName.setEnabled(true);
                        }
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
    //弹出底部窗口
    private void getPoP(int type) {
        popupWindows_pop = new PopupWindows_pop(getActivity(), type);
    }

    /**
     * 弹出popup 选择底部弹出
     *
     * @author lijipei
     */
    public class PopupWindows_pop extends PopupWindow {
        public PopupWindows_pop(Context mContext,int type) {
            View view = View.inflate(mContext, R.layout.item_pop_query, null);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            // 设置SelectPicPopupWindow弹出窗体动画效果
            // this.setAnimationStyle(R.style.hh_window_share_anim);
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(lin, Gravity.BOTTOM, 0, 0);
            View cView = view.findViewById(R.id.item_popupwindows_view);
            TextView quxiao = (TextView) view
                    .findViewById(R.id.item_pop_quxiao);
            TextView m_pop_text = (TextView) view
                    .findViewById(R.id.m_pop_text);
            //设置头部标题
            if (type == 1) {
                m_pop_text.setText("选择党委");
            } else if (type == 2) {
                m_pop_text.setText("选择总支");
            } else if (type == 3) {
                m_pop_text.setText("选择支部");
            } else if (type == 4) {
                m_pop_text.setText("选择人员");
            } else {
                m_pop_text.setText("查询结果");

            }
            listView = (ListView) view.findViewById(R.id.item_pop_list);
            //设置数据
            getData(type);
            cView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dismiss();
                }
            });
            quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dismiss();
                }
            });


        }
    }

    //设置数据
    private void getData(final int type) {
        if (type == 1) {
            ArrayList<PopBean> list1 = new ArrayList<>();
            PopBean phBean1 = new PopBean("DT000001", "中共山西省中条山国有林管理局委员会");
            list1.add(phBean1);
            getSetPop(list1, type);
        } else if (type == 2) {
            List<PopBean> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("deptNumber", deptNumberdw);
            RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .getQuery(map, MyApplication.getLoginBean().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseObserver<QueryPopBean>(getActivity()) {
                        @Override
                        protected void onNext(QueryPopBean response) {
                            Log.e("onNext= ", response.toString());
                            if (response == null) return;
                            List<QueryPopBean.DeptListBean> deptList = response.getDeptList();
                            if (deptList.size() != 0) {
                                for (int i = 0; i < deptList.size(); i++) {
                                    PopBean phBean1 = new PopBean(deptList.get(i).getDeptNumber(), deptList.get(i).getAbbreviation());
                                    list.add(phBean1);
                                }
                                getSetPop(list, type);
                            }else{
                                toast("暂无权限");
                                popupWindows_pop.dismiss();
                            }
                        }

                        @Override
                        protected void onError(Throwable e) {
                            super.onError(e);
                            Log.e("Throwable= ", e.getMessage());
                        }
                    }.actual());
        } else if (type == 3) {
            List<PopBean> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("deptNumber", deptNumberzz);
            RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .getQuery(map, MyApplication.getLoginBean().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseObserver<QueryPopBean>(getActivity()) {
                        @Override
                        protected void onNext(QueryPopBean response) {
                            Log.e("onNext= ", response.toString());
                            if (response == null) return;
                            List<QueryPopBean.DeptListBean> deptList = response.getDeptList();
                            if (deptList.size() != 0) {
                                for (int i = 0; i < deptList.size(); i++) {
                                    PopBean phBean1 = new PopBean(deptList.get(i).getDeptNumber(), deptList.get(i).getAbbreviation());
                                    list.add(phBean1);
                                }
                                getSetPop(list, type);
                            }else{
                                toast("暂无权限");
                                popupWindows_pop.dismiss();
                            }
                        }

                        @Override
                        protected void onError(Throwable e) {
                            super.onError(e);
                            Log.e("Throwable= ", e.getMessage());
                        }
                    }.actual());
        }
    }


    private void getSetPop(final List<PopBean> data, int type) {
        final MyAdapter adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String balance = data.get(position).getName() + "";
                if (type == 1) {
                    tv_dw_et.setText(balance);
                    deptNumberdw = data.get(position).getId();
                } else if (type == 2) {
                    tv_zz_et.setText(balance);
                    deptNumberzz = data.get(position).getId();
                } else if (type == 3) {
                    tv_zb_et.setText(balance);
                    deptNumberzb = data.get(position).getId();
                    deptNumberzb_name = data.get(position).getName();
                }
                popupWindows_pop.dismiss();
            }
        });
    }

    //适配器（党委  总支  支部  人员）
    public class MyAdapter extends BaseAdapter {
        private List<PopBean> stuList;
        private LayoutInflater inflater;
        public MyAdapter(List<PopBean> stuList) {
            this.stuList = stuList;
            this.inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return stuList == null ? 0 : stuList.size();
        }

        @Override
        public PopBean getItem(int position) {
            return stuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //加载布局为一个视图
            View view = inflater.inflate(R.layout.item_pop_text, null);
            TextView tv_name = (TextView) view.findViewById(R.id.text_mm);
            tv_name.setText(stuList.get(position).getName());
            return view;
        }
    }
}
