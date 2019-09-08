package com.lfc.zhihuidangjianapp.ui.activity.items;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.databinding.ItemInputFunctionBinding;
import com.lfc.zhihuidangjianapp.ui.activity.chat.InputFunctionFragment;
import com.lfc.zhihuidangjianapp.ui.activity.model.InputFunction;

import cn.nekocode.items.view.ItemEvent;
import cn.nekocode.items.view.RecyclerViewItemView;

/**
 * @date: 2018/8/7
 * @autror: guojian
 * @description:
 */

public class InputFunctionItemView extends RecyclerViewItemView<InputFunction> {

    private ItemInputFunctionBinding mBinding;

    @NonNull
    @Override
    public View onCreateItemView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_input_function, parent, false);
        return mBinding.getRoot();
    }

    @Override
    public void onBindData(@NonNull InputFunction data) {
        mBinding.ivIcon.setImageResource(data.getImgRes());
        mBinding.tvTitle.setText(data.getTitle());
        mBinding.getRoot().setOnClickListener(item->{
            getEventHandler().sendEvent(new ItemEvent(InputFunctionFragment.EVENT_ITEM_INPUT_FUNCTION, data, getViewHolder().getAdapterPosition()));
        });
    }

}
