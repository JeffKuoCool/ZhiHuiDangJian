package com.lfc.zhihuidangjianapp.ui.activity.items;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.databinding.ItemMessageBinding;
import com.lfc.zhihuidangjianapp.databinding.WindowMessageIteBinding;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.ui.activity.chat.ChatActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.MessageFragment;
import com.lfc.zhihuidangjianapp.ui.activity.model.Conversation;
import com.lfc.zhihuidangjianapp.utlis.CommonUtils;
import com.lfc.zhihuidangjianapp.widget.CustomPopWindow;

import cn.nekocode.items.view.ItemEvent;
import cn.nekocode.items.view.RecyclerViewItemView;

public class MessageItemView extends RecyclerViewItemView<Conversation> {

    private ItemMessageBinding mBinding;
    private EMMessage lastMessage;
    private WindowMessageIteBinding windowMessageIteBinding;

    @NonNull
    @Override
    public View onCreateItemView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        windowMessageIteBinding = DataBindingUtil.inflate(inflater, R.layout.window_message_ite, parent, false);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_message, parent, false);
        return mBinding.getRoot();
    }

    @Override
    public void onBindData(@NonNull Conversation data) {
        EMConversation emConversation = data.getEmConversation();
        lastMessage = emConversation.getLatestMessageFromOthers();
        if(lastMessage==null){
            lastMessage = emConversation.getLastMessage();
        }
        EMMessageBody lastMessageBody = lastMessage.getBody();
        if (lastMessage.getType() == EMMessage.Type.TXT) {
            mBinding.tvDec.setText(((EMTextMessageBody) lastMessageBody).getMessage());
        } else if (lastMessage.getType() == EMMessage.Type.VOICE) {
            mBinding.tvDec.setText("「语音消息」");
        } else if(lastMessage.getType() == EMMessage.Type.IMAGE){
            mBinding.tvDec.setText("「图片」");
        }
        mBinding.tvTime.setText(CommonUtils.timeStamp2Date(lastMessage.getMsgTime() + "", ""));
        int unReadCount = emConversation.getUnreadMsgCount();
        if (unReadCount == 0) {
            mBinding.tvMessageCount.setVisibility(View.GONE);
        } else {
            mBinding.tvMessageCount.setVisibility(View.VISIBLE);
            mBinding.tvMessageCount.setText(emConversation.getUnreadMsgCount() + "");
        }
        try {
            String userHeadimgurlResource = lastMessage.getStringAttribute("userHeadimgurlResource");
            String userNickname = lastMessage.getStringAttribute("userNickname");
            mBinding.tvUsername.setText(userNickname);
            Glide.with(mBinding.ivHead.getContext()).load(userHeadimgurlResource).into(mBinding.ivHead);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        mBinding.getRoot().setOnClickListener(item -> {
            //TODO 私信
            Intent intent = new Intent(data.getContext(), ChatActivity.class);
            intent.putExtra("user", data.getChatUsername());
            data.getContext().startActivity(intent);
        });

        mBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = getViewHolder().getAdapterPosition();
                int gravity = position > 1 ? Gravity.TOP : Gravity.BOTTOM;
                CustomPopWindow popWindow = new CustomPopWindow.Builder(data.getContext())
                        .setwidth(LinearLayout.LayoutParams.MATCH_PARENT)
                        .setheight(LinearLayout.LayoutParams.WRAP_CONTENT)
                        .setContentView(windowMessageIteBinding.getRoot())
                        .setBgAlpha(1)
                        .setFouse(true)
                        .setOutSideCancel(true)
                        .builder()
                        .showAtLocation(R.layout.item_message, Gravity.NO_GRAVITY, 0, calculateY(gravity));
                windowMessageIteBinding.tvMark.setOnClickListener(mark -> {
                    //标记已读消息
                    getEventHandler().sendEvent(new ItemEvent(MessageFragment.BUS_MARK_MESSAGE, data, position));
                    popWindow.dismiss();
                });
                windowMessageIteBinding.tvDeleteConvercition.setOnClickListener(delete -> {
                    //删除消息
                    getEventHandler().sendEvent(new ItemEvent(MessageFragment.BUS_DELETE_MESSAGE, data, position));
                    popWindow.dismiss();
                });
                return false;
            }
        });

    }

    private int calculateY(int gravity) {
        windowMessageIteBinding.getRoot().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int measuredHeight = windowMessageIteBinding.getRoot().getMeasuredHeight();
        int[] location = new int[2];
        mBinding.getRoot().getLocationOnScreen(location);

        if (gravity == Gravity.BOTTOM) {
            return location[1];
        } else if (gravity == Gravity.TOP) {
            return location[1] - measuredHeight;
        } else {
            return 0;
        }
    }


}
