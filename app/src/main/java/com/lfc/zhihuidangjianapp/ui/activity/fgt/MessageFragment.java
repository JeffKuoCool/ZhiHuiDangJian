package com.lfc.zhihuidangjianapp.ui.activity.fgt;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.databinding.FragmentMessageBinding;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.items.MessageItemsAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.model.Conversation;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import cn.nekocode.items.data.ItemData;
import cn.nekocode.items.view.ItemEvent;

/**
 * @autror: guojian
 * @description:消息列表
 */

public class MessageFragment extends BaseFragment {

    private FragmentMessageBinding mBinding;
    private MessageItemsAdapter mAdapter;
    private int page = 1;
    private int pageSize = 15;
    private Map<String, EMConversation> conversations;
    //消息是否全部已读
    private boolean isAllRead = true;

    public static final int BUS_MARK_MESSAGE = 0x15;    // 标记已读消息

    public static final int BUS_DELETE_MESSAGE = 0x16;    // 删除消息

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        setRecyclerView();
        setEvent();
        loadMessage();
        return mBinding.getRoot();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {

    }

    private void loadMessage() {
        //每次加载初始化默认消息全部已读
        isAllRead = true;
        conversations = EMClient.getInstance().chatManager().getAllConversations();
        mAdapter.getDataCollection().clear();
        for (Map.Entry<String, EMConversation> entry : conversations.entrySet()) {
            int unReadCount = entry.getValue().getUnreadMsgCount();
            if (unReadCount > 0) {
                isAllRead = false;
            }
            mAdapter.getDataCollection().add(mAdapter.Conversation(new Conversation(entry.getKey(), entry.getValue(), getContext())));
        }
        //按接收消息时间倒序排序
        Collections.sort(mAdapter.getDataCollection(), new Comparator<ItemData>() {
            @Override
            public int compare(ItemData itemData, ItemData t1) {
                return Integer.parseInt(String.valueOf(((Conversation) t1.getData()).getEmConversation().getLastMessage().getMsgTime() - ((Conversation) itemData.getData()).getEmConversation().getLastMessage().getMsgTime()));
            }
        });
        mAdapter.notifyDataSetChanged();
        mBinding.refreshLayout.finishRefresh();
        //TODO 导航栏显示有未读消息
//        RxBus.get().post(new BusEvent( Constants.BUS_RED_POINT,isAllRead));
    }

    /**
     * 所有回话是否全部已读
     * @return
     */
    private boolean isAllReadMessage(){
        boolean isAllRead = true;
        conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (Map.Entry<String, EMConversation> entry : conversations.entrySet()) {
            int unReadCount = entry.getValue().getUnreadMsgCount();
            if (unReadCount > 0) {
                isAllRead = false;
            }
        }
        return isAllRead;
    }

    private void setEvent() {
        //消息列表订阅事件
        mAdapter.addEventListener(new MessageItemsAdapter.EventListener() {
            @Override
            public void onMessageItemViewEvent(@NonNull ItemEvent<Conversation> event) {
                String chatUserId = event.getData().getChatUsername();
                int position = (int) event.getExtra();
                if (event.getWhat() == BUS_MARK_MESSAGE) {
                    // 标记已读消息
                    EMClient.getInstance().chatManager().getConversation(chatUserId).markAllMessagesAsRead();
                    mAdapter.notifyItemChanged(position);
                } else if (event.getWhat() == BUS_DELETE_MESSAGE) {
                    // 删除消息
                    EMClient.getInstance().chatManager().deleteConversation(chatUserId, true);
                    mAdapter.getDataCollection().remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
                //TODO 标记已读
//                RxBus.get().post(new BusEvent( Constants.BUS_RED_POINT,isAllRead));
            }
        });
        mBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadMessage();
            }
        });
    }

    private void setRecyclerView() {
        mAdapter = new MessageItemsAdapter();
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);
        final DividerItemDecoration decoration = new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getContext(), R.color.dark),
                DispalyUtil.dp2px(getContext(), 1),
                0, 0, false
        );
        mBinding.recyclerView.addItemDecoration(decoration);
    }

    @Subscribe
    public void refreshChatList(EMMessage emMessage) {
        if (emMessage != null) {
            loadMessage();
        }
    }


}
