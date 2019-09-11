package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
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
import com.lfc.zhihuidangjianapp.utlis.LocationUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.superrtc.ContextUtils.getApplicationContext;

/**
 * @date: 2019-08-09
 * @autror: guojian
 * @description: 党建矩阵详情
 */
public class Fgt_Dept_detail extends BaseFragment implements LocationSource {

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
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private LocationUtil locationUtil;
    private LinearLayout rv_zblb;
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
        init();
        //点击支部列表进行显示隐藏
        rv_zblb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

       //根据经纬度进行定位.draggable(true)
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(dept.getLatitude(),dept.getLongitude())));
        //添加定位图标
        aMap.addMarker(locationUtil.getMarkerOption(dept.getDeptAddress(),dept.getLatitude(),dept.getLongitude())).showInfoWindow();



    }

    private void init() {
        if(aMap == null){
            aMap = mapView.getMap();
        }

        setLocationCallBack();

        //设置定位监听
        aMap.setLocationSource(this);
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //显示定位层并可触发，默认false
        aMap.setMyLocationEnabled(true);
    }

    private void setLocationCallBack(){

        locationUtil = new LocationUtil();

         //获取当前定位
        /* locationUtil.setLocationCallBack(new LocationUtil.ILocationCallBack() {
            @Override
            public void callBack(String str, double lats, double lgts, AMapLocation aMapLocation) {

                Log.i("yy--",lat +"===" +lgt +"==="+lats +"===" +lgts);
                //根据获取的经纬度，将地图移动到定位位置
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lats,lgts)));
                mListener.onLocationChanged(aMapLocation);
                //添加定位图标
                aMap.addMarker(locationUtil.getMarkerOption(str,lats,lgts));
            }
        });*/

    }

    //定位激活回调
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;

        locationUtil.startLocate(getApplicationContext());
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        //暂停地图的绘制
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁地图
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //重新绘制加载地图
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
