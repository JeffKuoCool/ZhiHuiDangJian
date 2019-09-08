package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dept;
import com.lfc.zhihuidangjianapp.ui.activity.model.DeptDetail;
import com.lfc.zhihuidangjianapp.ui.activity.model.DeptDetailUser;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-09
 * @autror: guojian
 * @description: 党建矩阵详情
 */
public class Fgt_Dept_detail extends BaseFragment {

    TextView tvTitle;
    TextView tvBriefIntrodection;
    TextView tvAddress;
    RecyclerView recyclerView, rvMember;
    private LinearLayout viewMember;
    private TextView tvDeptTitle, tvDirectorTitle;
    private String deptNumber;
    private View mRootView;
    private int position;
    private MapView mapView;
    private TextView rv_zblb;
    private LinearLayout rv_zblb_lin;
    private ImageView rv_zblb_img;
    boolean falg = true; //true  是开启 false 关闭

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dept_detail;
    }

    @Override
    protected void initView(View rootView) {
        mRootView = rootView;
        tvTitle = rootView.findViewById(R.id.tv_title);
        tvBriefIntrodection = rootView.findViewById(R.id.tv_brief_introduction);
        tvAddress = rootView.findViewById(R.id.tv_address);
        recyclerView = rootView.findViewById(R.id.rv_group);
        rvMember = rootView.findViewById(R.id.rv_member);
        viewMember = rootView.findViewById(R.id.view_member);
        tvDeptTitle = rootView.findViewById(R.id.tv_dept_title);
        tvDirectorTitle = rootView.findViewById(R.id.tv_director_title);
        mapView = rootView.findViewById(R.id.mapView);
        //支部成员列表id
        rv_zblb = rootView.findViewById(R.id.rv_zblb);
        rv_zblb_lin = rootView.findViewById(R.id.rv_zblb_lin);
        rv_zblb_img = rootView.findViewById(R.id.rv_zblb_img);
        mapView.onCreate(((BaseActivity) getActivity()).savedInstanceState);
    }

    @Override
    protected void initData() {
        //点击支部列表进行显示隐藏
        rv_zblb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //优惠券
                if (falg) {
                    rv_zblb_img.setImageResource(R.mipmap.jt_top);
                    falg = false;
                    rv_zblb_lin.setVisibility(View.VISIBLE);
                } else {
                    rv_zblb_img.setImageResource(R.mipmap.jt_down);
                    falg = true;
                    rv_zblb_lin.setVisibility(View.GONE);
                }
            }
        });
        deptNumber = getArguments().getString("deptNumber");
        position = getArguments().getInt("position");
        if (position == 0) {
            tvDeptTitle.setText("党委介绍");
            tvDirectorTitle.setText("党委成员");
        } else if (position == 1) {
            tvDeptTitle.setText("支部介绍");
            tvDirectorTitle.setText("支委成员");
        } else if (position == 2) {
            tvDeptTitle.setText("支部介绍");
            tvDirectorTitle.setText("支委成员");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("deptNumber", deptNumber);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryDeptDetail(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<DeptDetail>(getActivity()) {

                    @Override
                    protected void onNext(DeptDetail response) {
                        Log.e("DeptDetail= ", response.toString());
                        if (response == null) {
                            return;
                        }
                        initDeptDetail(response);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    private void initDeptDetail(DeptDetail response) {
        Dept dept = response.getDept();
        if (dept == null) {
            return;
        }
//        ButterKnife.bind(this);
        tvTitle.setText(dept.getDeptName());
        if (dept.getBriefIntroduction() != null) {
            tvBriefIntrodection.setText(dept.getBriefIntroduction());
        } else {
            mRootView.findViewById(R.id.view_introduction).setVisibility(View.GONE);
        }
        tvAddress.setText(dept.getDeptAddress());

        if (response.getUserlist() != null && !response.getUserlist().isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
            recyclerView.setAdapter(new CommonAdapter<DeptDetailUser>(MyApplication.getAppContext(), R.layout.item_dept_user, response.getUserlist()) {
                @Override
                protected void convert(ViewHolder holder, DeptDetailUser data, int position) {
                    holder.setText(R.id.tv_name, data.getDisplayName());
                    holder.setText(R.id.tv_content, data.getSubordinatePartyGroup());
                    holder.setText(R.id.tv_tell, data.getMobileNumber());
                }

            });
        } else {
            viewMember.setVisibility(View.GONE);
        }
        List<String> members = response.getDirectorNameList();
        if (members != null && !members.isEmpty()) {
            rvMember.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMember.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_director_member, members) {
                @Override
                protected void convert(ViewHolder holder, String data, int position) {
                    holder.setText(R.id.tv_content, data);
                }

            });
        } else {
            mRootView.findViewById(R.id.tv_director_title).setVisibility(View.GONE);
        }

        LatLng latLng = new LatLng(39.906901, 116.397972);

        Log.i("yy",dept.getLatitude() +"===" +dept.getLongitude());
        mapView.getMap().setPointToCenter((int) dept.getLatitude(), (int) dept.getLongitude());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }
}
