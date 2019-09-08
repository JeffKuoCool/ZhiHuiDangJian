package com.lfc.zhihuidangjianapp.ui.activity.chat;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.databinding.FragmentInputFunctionBinding;
import com.lfc.zhihuidangjianapp.ui.activity.items.InputFunctionItemsAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.model.InputFunction;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import cn.nekocode.items.view.ItemEvent;

/**
 * @date: 2019/3/27
 * @autror: guojian
 * @description: 聊天控件功能扩展列表
 */
public class InputFunctionFragment extends BaseFragment {

    public static final int EVENT_ITEM_INPUT_FUNCTION = 0;

    private static final int ITEM_ICEBREAKING = 2;

    private static final int ITEM_CAMERA = 0;

    private static final int ITEM_TAKE_THOTO = 1;

    private int[] imgs = {
            R.mipmap.d_icon_add_img,
 R.mipmap.d_icon_add_camera,
            R.mipmap.d_icon_sayhi};

    private String[] titles = {
            "照片",
 "拍摄",
            "破冰语"};

    private InputFunctionItemsAdapter adapter;

    private FragmentInputFunctionBinding mBinding;

    /**
     * 列表列数
     */
    private int spanCount = 4;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_function, container, false);
        initView();
        setEvent();
        return mBinding.getRoot();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {

    }

    private void setEvent() {
        adapter.addEventListener(new InputFunctionItemsAdapter.EventListener(){
            @Override
            public void onInputFunctionItemViewEvent(@NonNull ItemEvent<InputFunction> event) {
                super.onInputFunctionItemViewEvent(event);
                if(event.getWhat() == EVENT_ITEM_INPUT_FUNCTION){
                    int position = (Integer) event.getExtra();
                    switch (position){
                        case ITEM_CAMERA://照片
                            //TODO 添加头像
                            PictureSelector.create(getActivity())
                                    .openGallery(PictureMimeType.ofImage())
                                    .compress(true)
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                            ((ChatActivity)getActivity()).hideAllViews();
                            break;
                        case ITEM_TAKE_THOTO:
                            PictureSelector.create(getActivity())
                                    .openCamera(PictureMimeType.ofImage())
                                    .compress(true)
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                            ((ChatActivity)getActivity()).hideAllViews();
                            break;

                        case ITEM_ICEBREAKING://破冰语
//                            getActivityRouter().gotoIceBreaking(getActivity());
                            ((ChatActivity)getActivity()).hideAllViews();
                            break;
                    }
                }
            }
        });
    }

    private void initView() {
        adapter = new InputFunctionItemsAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(adapter);
        for (int i = 0; i<imgs.length; i++) {
            adapter.getDataCollection().add(adapter.InputFunction(new InputFunction(imgs[i], titles[i])));
        }
        adapter.notifyDataSetChanged();
    }

}
