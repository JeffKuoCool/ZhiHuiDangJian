package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Dept_Detail;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.QueryDepMemberBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dept;
import com.lfc.zhihuidangjianapp.ui.activity.model.DeptDetail;
import com.lfc.zhihuidangjianapp.ui.activity.model.DeptDetailUser;
import com.lfc.zhihuidangjianapp.ui.activity.model.Depts;
import com.lfc.zhihuidangjianapp.utlis.LocationUtil;
import com.lfc.zhihuidangjianapp.widget.BezierView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.alipay.sdk.widget.j.r;
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
    RecyclerView recyclerView, rvMember,dept_detail_tl_recyclerView,recyclerView2,recyclerView3,recyclerView4,recyclerView5;
    private BezierView bezierOrganize, bezierReport;
    private LinearLayout viewMember, viewOrganize, viewReport,dept_detail_tl_lin,viewMember2,viewMember3,viewMember4,viewMember5;
    private TextView tvDeptTitle, tvDirectorTitle;
    private String deptNumber;
    private View mRootView;
    private int position;
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private LocationUtil locationUtil;
    private LinearLayout rv_zblb,rv_zblb2,rv_zblb3,rv_zblb4,rv_zblb5;
    private LinearLayout rv_zblb_lin,rv_zblb_lin2,rv_zblb_lin3,rv_zblb_lin5,rv_zblb_lin4;
    private ImageView rv_zblb_img,rv_zblb_img2,rv_zblb_img3,rv_zblb_img4,rv_zblb_img5;
    boolean falg = true; //true  是开启 false 关闭
    boolean falg2 = true; //true  是开启 false 关闭
    boolean falg3 = true; //true  是开启 false 关闭
    boolean falg4 = true; //true  是开启 false 关闭
    boolean falg5 = true; //true  是开启 false 关闭
    private Depts mDepts;
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
        recyclerView2 = rootView.findViewById(R.id.rv_group2);
        recyclerView3 = rootView.findViewById(R.id.rv_group3);
        recyclerView4 = rootView.findViewById(R.id.rv_group4);
        recyclerView5 = rootView.findViewById(R.id.rv_group5);
        rvMember = rootView.findViewById(R.id.rv_member);
        viewMember = rootView.findViewById(R.id.view_member);
        viewMember2 = rootView.findViewById(R.id.view_member2);
        viewMember3 = rootView.findViewById(R.id.view_member3);
        viewMember4 = rootView.findViewById(R.id.view_member4);
        viewMember5 = rootView.findViewById(R.id.view_member5);
        tvDeptTitle = rootView.findViewById(R.id.tv_dept_title);
        tvDirectorTitle = rootView.findViewById(R.id.tv_director_title);
        mapView = rootView.findViewById(R.id.mapView);
        //支部成员列表id第一党
        rv_zblb = rootView.findViewById(R.id.rv_zblb);
        rv_zblb_lin = rootView.findViewById(R.id.rv_zblb_lin);
        rv_zblb_img = rootView.findViewById(R.id.rv_zblb_img);
        //支部成员列表id第二党
        rv_zblb2 = rootView.findViewById(R.id.rv_zblb2);
        rv_zblb_lin2 = rootView.findViewById(R.id.rv_zblb_lin2);
        rv_zblb_img2 = rootView.findViewById(R.id.rv_zblb_img2);
        //支部成员列表id第三党
        rv_zblb3 = rootView.findViewById(R.id.rv_zblb3);
        rv_zblb_lin3 = rootView.findViewById(R.id.rv_zblb_lin3);
        rv_zblb_img3 = rootView.findViewById(R.id.rv_zblb_img3);
        //支部成员列表id第四党
        rv_zblb4 = rootView.findViewById(R.id.rv_zblb4);
        rv_zblb_lin4 = rootView.findViewById(R.id.rv_zblb_lin4);
        rv_zblb_img4 = rootView.findViewById(R.id.rv_zblb_img4);
        //支部成员列表id第5党
        rv_zblb5 = rootView.findViewById(R.id.rv_zblb5);
        rv_zblb_lin5 = rootView.findViewById(R.id.rv_zblb_lin5);
        rv_zblb_img5 = rootView.findViewById(R.id.rv_zblb_img5);

        bezierOrganize = rootView.findViewById(R.id.bezierOrganize);
        bezierReport = rootView.findViewById(R.id.bezierReport);
        viewOrganize = rootView.findViewById(R.id.viewOrganize);
        viewReport = rootView.findViewById(R.id.viewReport);

        //离退休支部
        dept_detail_tl_recyclerView=rootView.findViewById(R.id.dept_detail_tl_recyclerView);
        dept_detail_tl_lin=rootView.findViewById(R.id.dept_detail_tl_lin);

        mapView.onCreate(((BaseActivity) getActivity()).savedInstanceState);
    }

    @Override
    protected void initData() {
        init();
        //点击第一党小组进行显示隐藏
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
        //点击第二党小组进行显示隐藏
        rv_zblb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (falg2) {
                    rv_zblb_img2.setImageResource(R.mipmap.jt_top);
                    falg2 = false;
                    rv_zblb_lin2.setVisibility(View.VISIBLE);
                } else {
                    rv_zblb_img2.setImageResource(R.mipmap.jt_down);
                    falg2 = true;
                    rv_zblb_lin2.setVisibility(View.GONE);
                }
            }
        });
        //点击第三党小组进行显示隐藏
        rv_zblb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (falg3) {
                    rv_zblb_img3.setImageResource(R.mipmap.jt_top);
                    falg3 = false;
                    rv_zblb_lin3.setVisibility(View.VISIBLE);
                } else {
                    rv_zblb_img3.setImageResource(R.mipmap.jt_down);
                    falg3 = true;
                    rv_zblb_lin3.setVisibility(View.GONE);
                }
            }
        });
        //点击第四党小组进行显示隐藏
        rv_zblb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (falg4) {
                    rv_zblb_img4.setImageResource(R.mipmap.jt_top);
                    falg4 = false;
                    rv_zblb_lin4.setVisibility(View.VISIBLE);
                } else {
                    rv_zblb_img4.setImageResource(R.mipmap.jt_down);
                    falg4 = true;
                    rv_zblb_lin4.setVisibility(View.GONE);
                }
            }
        });
        //点击第五党小组进行显示隐藏
        rv_zblb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (falg5) {
                    rv_zblb_img5.setImageResource(R.mipmap.jt_top);
                    falg5 = false;
                    rv_zblb_lin5.setVisibility(View.VISIBLE);
                } else {
                    rv_zblb_img5.setImageResource(R.mipmap.jt_down);
                    falg5 = true;
                    rv_zblb_lin5.setVisibility(View.GONE);
                }
            }
        });

        deptNumber = getArguments().getString("deptNumber");
        position = getArguments().getInt("position");
        //deptNumber==10002显示离退休支部
        if(deptNumber.equals("10002")){
            loadData();
        }
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
        //请求党委成员
        Map<String, Object> map2 = new HashMap<>();
        Log.i("yydeptNumber",deptNumber);
        map2.put("deptNumber", deptNumber);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryDeptMember(map2, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<QueryDepMemberBean>(getActivity()) {

                    @Override
                    protected void onNext(QueryDepMemberBean response) {
                        if (response == null) {
                            return;
                        }
                        List<QueryDepMemberBean.DeptMemberListBean> deptMemberList = response.getDeptMemberList();
                        Log.i("yydeptMemberList",deptMemberList.size()+"");
                        if (deptMemberList != null && !deptMemberList.isEmpty()) {
                            // rvMember.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            rvMember.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvMember.setAdapter(new CommonAdapter<QueryDepMemberBean.DeptMemberListBean>(getActivity(), R.layout.item_director_member, deptMemberList) {
                                @Override
                                protected void convert(ViewHolder holder, QueryDepMemberBean.DeptMemberListBean data, int position) {
                                    if(data.getTypeName().length()==4){
                                        String substring = data.getTypeName().substring(0, 1);
                                        String substring1 = data.getTypeName().substring(1, 2);
                                        String substring2 = data.getTypeName().substring(2, 3);
                                        String substring3 = data.getTypeName().substring(3, 4);
                                        //　"\u3000" 加空格
                                        holder.setText(R.id.tv_content, substring+" "+substring1+"  "+substring2+" "+substring3+":");
                                    }else{
                                        holder.setText(R.id.tv_content, data.getTypeName()+":");
                                    }
                                    holder.setText(R.id.tv_content2, data.getName());

                                }

                            });
                        } else {
                            mRootView.findViewById(R.id.tv_director_title).setVisibility(View.GONE);
                        }
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
            List<DeptDetailUser> userlist1 = new ArrayList<>();

            List<DeptDetailUser> userlist2 = new ArrayList<>();

            List<DeptDetailUser> userlist3 = new ArrayList<>();
            List<DeptDetailUser> userlist4 = new ArrayList<>();
            List<DeptDetailUser> userlist5 = new ArrayList<>();

            List<DeptDetailUser> userlist = response.getUserlist();

            for(int i =0;i<userlist.size();i++){
                if(userlist.get(i).getSubordinatePartyGroup().equals("第一党小组")){
                    userlist1.add(userlist.get(i));
                }else  if(userlist.get(i).getSubordinatePartyGroup().equals("第二党小组")){
                    userlist2.add(userlist.get(i));
                }else  if(userlist.get(i).getSubordinatePartyGroup().equals("第三党小组")){
                    userlist3.add(userlist.get(i));
                }else  if(userlist.get(i).getSubordinatePartyGroup().equals("第四党小组")){
                    userlist4.add(userlist.get(i));
                }else  if(userlist.get(i).getSubordinatePartyGroup().equals("第五党小组")){
                    userlist5.add(userlist.get(i));
                }
            }

            if(userlist1.size()!=0){
                viewMember.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
                recyclerView.setAdapter(new CommonAdapter<DeptDetailUser>(MyApplication.getAppContext(), R.layout.item_dept_user, userlist1) {
                    @Override
                    protected void convert(ViewHolder holder, DeptDetailUser data, int position) {
                        holder.setText(R.id.tv_name, data.getDisplayName());
                        holder.setText(R.id.tv_content, data.getPartyPosts()+"");
                        holder.setText(R.id.tv_tell, data.getMobileNumber());
                    }

                });
            }else{
                viewMember.setVisibility(View.GONE);
            }

            if(userlist2.size()!=0){
                viewMember2.setVisibility(View.VISIBLE);
                recyclerView2.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
                recyclerView2.setAdapter(new CommonAdapter<DeptDetailUser>(MyApplication.getAppContext(), R.layout.item_dept_user, userlist2) {
                    @Override
                    protected void convert(ViewHolder holder, DeptDetailUser data, int position) {
                        holder.setText(R.id.tv_name, data.getDisplayName());
                        holder.setText(R.id.tv_content, data.getPartyPosts()+"");
                        holder.setText(R.id.tv_tell, data.getMobileNumber());
                    }

                });
            }else {
                viewMember2.setVisibility(View.GONE);
            }
            if(userlist3.size()!=0){
                viewMember3.setVisibility(View.VISIBLE);
                recyclerView3.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
                recyclerView3.setAdapter(new CommonAdapter<DeptDetailUser>(MyApplication.getAppContext(), R.layout.item_dept_user, userlist3) {
                    @Override
                    protected void convert(ViewHolder holder, DeptDetailUser data, int position) {
                        holder.setText(R.id.tv_name, data.getDisplayName());
                        holder.setText(R.id.tv_content,data.getPartyPosts()+"");
                        holder.setText(R.id.tv_tell, data.getMobileNumber());
                    }

                });
            }else{
                viewMember3.setVisibility(View.GONE);
            } if(userlist4.size()!=0){
                viewMember4.setVisibility(View.VISIBLE);
                recyclerView4.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
                recyclerView4.setAdapter(new CommonAdapter<DeptDetailUser>(MyApplication.getAppContext(), R.layout.item_dept_user, userlist4) {
                    @Override
                    protected void convert(ViewHolder holder, DeptDetailUser data, int position) {
                        holder.setText(R.id.tv_name, data.getDisplayName());
                        holder.setText(R.id.tv_content, data.getPartyPosts()+"");
                        holder.setText(R.id.tv_tell, data.getMobileNumber());
                    }

                });
            }else {
                viewMember4.setVisibility(View.GONE);
            }
            if(userlist5.size()!=0){
                viewMember5.setVisibility(View.VISIBLE);
                recyclerView5.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
                recyclerView5.setAdapter(new CommonAdapter<DeptDetailUser>(MyApplication.getAppContext(), R.layout.item_dept_user, userlist5) {
                    @Override
                    protected void convert(ViewHolder holder, DeptDetailUser data, int position) {
                        holder.setText(R.id.tv_name, data.getDisplayName());
                        holder.setText(R.id.tv_content, data.getPartyPosts()+"");
                        holder.setText(R.id.tv_tell, data.getMobileNumber());
                    }

                });
            }else{
                viewMember5.setVisibility(View.GONE);
            }
        }
       /* List<String> members = response.getDirectorNameList();
        if (members != null && !members.isEmpty()) {
            rvMember.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            // rvMember.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMember.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_director_member, members) {
                @Override
                protected void convert(ViewHolder holder, String data, int position) {
                    holder.setText(R.id.tv_content, data);
                }

            });
        } else {
            mRootView.findViewById(R.id.tv_director_title).setVisibility(View.GONE);
        }*/

        //根据经纬度进行定位.draggable(true)
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(dept.getLatitude(), dept.getLongitude())));
        //添加定位图标
        aMap.addMarker(locationUtil.getMarkerOption(dept.getDeptAddress(), dept.getLatitude(), dept.getLongitude())).showInfoWindow();

        if(response.getoLisfForEacherList()==null||response.getoLisfForEacherList().isEmpty()){
            viewOrganize.setVisibility(View.GONE);
        }else{
            viewOrganize.setVisibility(View.VISIBLE);
            bezierOrganize.setData(response.getoLisfForEacherList());
        }

        if(response.getWeekEacherList()==null||response.getWeekEacherList().isEmpty()){
            viewReport.setVisibility(View.GONE);
        }else{
            viewReport.setVisibility(View.VISIBLE);
            bezierReport.setData(response.getWeekEacherList());
        }
    }

    private void init() {
        if (aMap == null) {
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

    private void setLocationCallBack() {

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
    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("deptNumber", "10001");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryDeptList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<Depts>(getActivity()) {
                    @Override
                    protected void onNext(Depts response) {
                        Log.i("yy-size",response.getDeptList().size()+"");
                        if(response.getDeptList() != null && !response.getDeptList().isEmpty()){
                            dept_detail_tl_lin.setVisibility(View.VISIBLE);
                            mDepts = response;
                            Log.e("onNext= ", response.toString());
                            dept_detail_tl_recyclerView.setLayoutManager(new GridLayoutManager(MyApplication.getAppContext(), 3));
                            List<Dept> deptList = response.getDeptList();
                            List<Dept> list = new ArrayList<>();
                            //判断列表包含退休党支部文案直接装到新的集合里面进行展示
                            for(int i =0;i<deptList.size();i++){
                                if(deptList.get(i).getAbbreviation().contains("退休党支部")){
                                    list.add(deptList.get(i));
                                }
                            }
                            dept_detail_tl_recyclerView.setAdapter(new CommonAdapter<Dept>(MyApplication.getAppContext(), R.layout.item_dept, list) {
                                @Override
                                protected void convert(ViewHolder holder, Dept dept, int position) {
                                    holder.setText(R.id.tv_dept, dept.getAbbreviation());
                                    ImageView image = holder.getView(R.id.image);
                                    image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                        @Override
                                        public void onGlobalLayout() {
                                            image.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                            setViewSize(image, image.getWidth(), image.getWidth());
                                        }
                                    });
                                    holder.getConvertView().setOnClickListener(item->{
                                        //TODO 党建矩阵详情
                                        Intent intent = new Intent(getActivity(), Act_Dept_Detail.class);
                                        intent.putExtra("deptNumber", dept.getDeptNumber());
                                        startActivity(intent);
                                    });
                                }

                            });

                        }else{
                            dept_detail_tl_lin.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    private void setViewSize(View view, int targetWidth, int targetHeight) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = targetWidth;
        params.height = targetHeight;
        view.setLayoutParams(params);
    }
}
