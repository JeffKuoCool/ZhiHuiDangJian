package com.lfc.zhihuidangjianapp.ui.activity.items;

import com.lfc.zhihuidangjianapp.ui.activity.model.InputFunction;

import cn.nekocode.items.annotation.ItemBinding;
import cn.nekocode.items.annotation.Items;

/**
 * @author snkso <snkso@foxmail.com>
 */
@Items(
        value = {
                @ItemBinding(
                        data = @ItemBinding.Data(InputFunction.class),
                        view = @ItemBinding.View(InputFunctionItemView.class)
                )
        }
)
public interface InputFunctionItems {
}
