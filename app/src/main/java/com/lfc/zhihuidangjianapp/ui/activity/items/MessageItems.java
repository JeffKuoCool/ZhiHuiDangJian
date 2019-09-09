package com.lfc.zhihuidangjianapp.ui.activity.items;

import com.lfc.zhihuidangjianapp.ui.activity.model.Conversation;

import cn.nekocode.items.annotation.ItemBinding;
import cn.nekocode.items.annotation.Items;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@Items(
        value = {
                @ItemBinding(
                        data = @ItemBinding.Data(Conversation.class),
                        view = @ItemBinding.View(MessageItemView.class)
                ),
        }
)
public interface MessageItems {
}
