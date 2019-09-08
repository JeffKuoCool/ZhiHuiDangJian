package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.QueryPopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @date: 2019-08-10
 * @autror: guojian
 * @description: 周报查询
 */
public class Fgt_Weekend_Query extends BaseFragment {
    @BindView(R.id.tv_dw_et)
    EditText tvDwEt;
    @BindView(R.id.tv_dw_lin)
    LinearLayout tvDwLin;
    @BindView(R.id.tv_zz_et)
    EditText tvZzEt;
    @BindView(R.id.tv_zz_lin)
    LinearLayout tvZzLin;
    @BindView(R.id.tv_zb_et)
    EditText tvZbEt;
    @BindView(R.id.tv_zb_lin)
    LinearLayout tvZbLin;
    @BindView(R.id.tv_ry_et)
    EditText tvRyEt;
    @BindView(R.id.tv_ry_lin)
    LinearLayout tvRyLin;
    @BindView(R.id.tv_quey)
    TextView tvQuey;
    @BindView(R.id.lin)
    LinearLayout lin;
    Unbinder unbinder;
    private PopupWindows_pop popupWindows_pop;
    private  ListView listView;
    //  private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.weekend_query;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
     /*   RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyWeeklyWorkReportPageList(MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponseWorkReport>(getActivity()) {

                    @Override
                    protected void onNext(ResponseWorkReport response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                        setRecyclerView(response.getWeeklyWorkReportList().getDatas());
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_dw_et, R.id.tv_zz_et, R.id.tv_zb_et, R.id.tv_ry_et, R.id.tv_quey})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dw_et:

                getPoP(1);
                break;
            case R.id.tv_zz_et:
                getPoP(2);
                break;
            case R.id.tv_zb_et:
                getPoP(3);
                break;
            case R.id.tv_ry_et:
                getPoP(4);
                break;
            case R.id.tv_quey:
                if (tvDwEt.getText().toString().trim().equals("")) {
                    ToastUtils.show("请选择党委");
                    return;
                }
                if (tvZzEt.getText().toString().trim().equals("")) {
                    ToastUtils.show("请选择总支");
                    return;
                }
                if (tvZbEt.getText().toString().trim().equals("")) {
                    ToastUtils.show("请选择支部");
                    return;
                }
                if(tvRyEt.getText().toString().trim().isEmpty()){
                    ToastUtils.show("请选择人员");
                    return;
                }
                toast(tvDwEt.getText().toString()+"=="+tvZzEt.getText().toString()+"=="+tvZbEt.getText().toString() +"=="+tvRyEt.getText().toString());
                break;
        }
    }


   //选择规格
   private void getPoP(int type) {
       popupWindows_pop = new PopupWindows_pop(getActivity(),type);
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
            if(type==1){
                m_pop_text.setText("选择党委");
            }else if(type==2){
                m_pop_text.setText("选择总支");
            }else if(type==3){
                m_pop_text.setText("选择支部");
            }else{
                m_pop_text.setText("选择人员");
            }
            listView = (ListView) view.findViewById(R.id.item_pop_list);
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
    private void getData(final int type) {
        ArrayList<QueryPopBean> list1 = new ArrayList<>();
        QueryPopBean phBean1 = new QueryPopBean(1, "选择党委1");
        QueryPopBean phBean2 = new QueryPopBean(2, "选择党委2");
        QueryPopBean phBean3 = new QueryPopBean(3, "选择党委3");
        list1.add(phBean1);
        list1.add(phBean2);
        list1.add(phBean3);
        ArrayList<QueryPopBean> list2 = new ArrayList<>();
        QueryPopBean phBean4 = new QueryPopBean(4, "选择总支1");
        QueryPopBean phBean41 = new QueryPopBean(4, "选择总支1");
        QueryPopBean phBean42 = new QueryPopBean(4, "选择总支1");
        QueryPopBean phBean43 = new QueryPopBean(4, "选择总支1");
        list2.add(phBean4);
        list2.add(phBean41);
        list2.add(phBean42);
        list2.add(phBean43);
        ArrayList<QueryPopBean> list3 = new ArrayList<>();
        QueryPopBean phBean5 = new QueryPopBean(5, "选择支部1");
        QueryPopBean phBean6 = new QueryPopBean(6, "选择支部2");
        QueryPopBean phBean7 = new QueryPopBean(7, "选择支部3");
        list3.add(phBean5);
        list3.add(phBean6);
        list3.add(phBean7);
        ArrayList<QueryPopBean> list4 = new ArrayList<>();
        QueryPopBean phBean8 = new QueryPopBean(8, "选择人员1");
        QueryPopBean phBean9 = new QueryPopBean(9, "选择人员2");
        QueryPopBean phBean10 = new QueryPopBean(10, "选择人员3");
        list4.add(phBean8);
        list4.add(phBean9);
        list4.add(phBean10);
        if (type == 1) {
            getSetBz(list1,type);
        } else if (type == 2) {
            getSetBz(list2,type);
        } else if(type==3){
            getSetBz(list3,type);
        }else{
            getSetBz(list4,type);
        }
    }

    private void getSetBz(final List<QueryPopBean> data,int type) {

        final MyAdapter adapter = new MyAdapter(data);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //cid =data.get(position).getCid()
                String balance = data.get(position).getName()+"";
                // hyType.setText("+" +balance+"");
                if(type==1){
                    tvDwEt.setText(balance);
                }else  if(type==2){
                    tvZzEt.setText(balance);
                }else  if(type==3){
                    tvZbEt.setText(balance);
                }else{
                    tvRyEt.setText(balance);
                }
                popupWindows_pop.dismiss();

            }
        });

    }

    public class MyAdapter extends BaseAdapter {

        private List<QueryPopBean> stuList;
        private LayoutInflater inflater;

        public MyAdapter(List<QueryPopBean> stuList) {
            this.stuList = stuList;
            this.inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return stuList == null ? 0 : stuList.size();
        }

        @Override
        public QueryPopBean getItem(int position) {
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
            /*   tv_name.setText(stuList.get(position).getBalance() + "  " + stuList.get(position).getShort_name());*/
            tv_name.setText(stuList.get(position).getName()+"");

            // Log.i("yy--address1", stuList.get(position).getToday_num() + "===" + stuList.get(position).getUsername());
            return view;
        }


    }
     /* private void setRecyclerView(List<WorkReport> workReportList) {
        if (workReportList == null || workReportList.isEmpty()) {
            for (int i = 0; i < 15; i++) {
                workReportList.add(new WorkReport());
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<WorkReport>(MyApplication.getAppContext(), R.layout.item_mine_work_report, workReportList) {
            @Override
            protected void convert(ViewHolder holder, WorkReport data, int position) {

            }

        });
    }*/
}
