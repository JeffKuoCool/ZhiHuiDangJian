package com.lfc.zhihuidangjianapp.ui.activity.chat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.Constants;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.chat.EazyChatApi;
import com.lfc.zhihuidangjianapp.databinding.ActivityChatBinding;
import com.lfc.zhihuidangjianapp.event.BusEvent;
import com.lfc.zhihuidangjianapp.inter.InputEvent;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.service.RecordVoiceService;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.ChatAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.model.Chat;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;
import com.lfc.zhihuidangjianapp.ui.activity.model.UserInfo;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tb.emoji.Emoji;
import com.tb.emoji.EmojiUtil;
import com.tb.emoji.FaceFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.nekocode.items.data.ItemData;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-09-07
 * @autror: guojian
 * @description: 聊天页
 */
public class ChatActivity extends BaseActivity implements FaceFragment.OnEmojiClickListener {

    private ActivityChatBinding mBinding;

    private RecordVoiceService mRecordVoiceService;

    private List<Chat> mMessageList = new ArrayList<>();

    private ChatAdapter mChatAdapter;

    private String chatUserId;

    private String username;

    private String imgUrl;

    private long lastTime;

    private int pageSize = 15;

    //设置每2分钟
    private long differenceTime = 120000;

    private FragmentTransaction ft;

    private InputFunctionFragment inputFunctionFragment;

    public static final int RECEIVE_SEND_MESSAGE = 0;
    private static final int NOTIFY_SEND = 2;
    private static final int NOTIFY_CLOSE_KEYBOARD = 1;

    private Timer timer;

    private PlayServiceConnection conn;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOTIFY_SEND:
                    mBinding.rvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                    break;
                /**
                 * 成功发送消息后通知关闭键盘
                 */
                case NOTIFY_CLOSE_KEYBOARD:
                    mBinding.rvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        mBinding.imgBack.setOnClickListener(back->finish());
        getPermissions();
        chatUserId = getIntent().getStringExtra("user");
        ft = getSupportFragmentManager().beginTransaction();
        initInputView();
        if (chatUserId == null) {
            return;
        }
        requstUserInfo(chatUserId);
        conn = new PlayServiceConnection();
        bindRecordService();
    }

    @Override
    protected void initData() {

    }

    private void initChatList() {
        if (chatUserId != null) {
            List<EMMessage> messageList = EazyChatApi.getMessageList(chatUserId, pageSize);
            List<Chat> history = getChatList(messageList, chatUserId);
            mChatAdapter.notify(history);
            mBinding.rvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
        }
    }

//    private void sort(List list){
//        Collections.sort(list, new Comparator<ItemData>() {
//            @Override
//            public int compare(ItemData itemData, ItemData t1) {
//                return Integer.parseInt(String.valueOf(((Conversation) t1.getData()).getEmConversation().getLastMessage().getMsgTime() - ((Conversation) itemData.getData()).getEmConversation().getLastMessage().getMsgTime()));
//            }
//        });
//    }

    private void requstUserInfo(String chatUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", chatUserId);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryUserByUserId( map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<UserInfo>(getActivity()) {

                    @Override
                    protected void onNext(UserInfo response) {
                        Log.e("onNext= ", response.toString());
                        if (response != null) {
                            username = response.getUser().getSealName();
                            imgUrl = response.getUser().getImgAddress();
                            initToolBar();
                            registEvent();
                            initChatList();
                        }
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    private List<Chat> getChatList(List<EMMessage> messageList, String from) {
        if (messageList == null || messageList.isEmpty()) {
            return null;
        }
        List<Chat> chats = new ArrayList<>();
        for (int i = 0; i < messageList.size(); i++) {
            EMMessage emMessage = messageList.get(i);
            Chat chat;
            if (TextUtils.equals(emMessage.getTo(), from)) {
                //我的消息
                chat = new Chat(emMessage, ChatAdapter.MY_INFOMATION, MyApplication.getmUserInfo().getUser().getImgAddress(),
                        MyApplication.getmUserInfo().getUser().getSealName());
            } else {
                //别人的消息
                chat = new Chat(emMessage, ChatAdapter.OTHER_INFOMATION, imgUrl,
                        username);
                chat.setContext(ChatActivity.this);
            }
            isShowMessageTime(chat, i, messageList);
            chats.add(chat);
        }
        return chats;
    }

    private void isShowMessageTime(Chat chat, int index, List<EMMessage> messageList) {
        if (index == 0) {
            chat.setShowMessageTime(true);
        } else if (messageList.get(index).getMsgTime() - messageList.get(index - 1).getMsgTime() > differenceTime) {
            chat.setShowMessageTime(true);
        }
    }

    private void initInputView() {
        mBinding.viewInputTip.setActivityContext(this);

        mChatAdapter = new ChatAdapter(mMessageList, this);
        mBinding.rvChat.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvChat.setAdapter(mChatAdapter);

        /**
         * 初始化聊天扩展功能（"+"扩展）
         */
        inputFunctionFragment = new InputFunctionFragment();
        ft.add(R.id.view_input_function, inputFunctionFragment);
        ft.commit();
        /**
         * TODO 关闭发送语音，表情入口
         */
        mBinding.viewInputTip.isSendVoiced(mBinding.voiceRecordView);
    }

    /**
     * 事件监听
     */
    private void registEvent() {
        mBinding.viewInputTip.addInputEvent(new InputEvent() {

            @Override
            public void showEmojiView() {
                closeInput();
                timer = new Timer();
                timer.schedule(new AnimationTask(NOTIFY_SEND), 200);
            }

            @Override
            public void showFunctionView(boolean isShowFunction) {
                closeInput();
                timer = new Timer();
                timer.schedule(new AnimationTask(NOTIFY_SEND), 200);
            }

            @Override
            public void sendMessage(String message) {
                sendTextMessage(message);
            }

            @Override
            public void sendVoice(String voicePath, int lenth) {
                sendVoiceMessage(voicePath, lenth, chatUserId);
            }

        });

//        mBinding.rvChat.setRefreshListener(this);
    }

    /**
     * 播放语音
     */
    @Subscribe
    public void rxBusEvent(BusEvent rxBusEvent) {
        if (rxBusEvent.getEvent() == Constants.BUS_PLAY_VOICE) {
            HashMap<String, Object> map = (HashMap<String, Object>) rxBusEvent.getEventObj();
            EMVoiceMessageBody voiceMessageBody = (EMVoiceMessageBody) map.get("body");
            ImageView imageView = (ImageView) map.get("image");
            ImageView imageBgVoice = (ImageView) map.get("imageBg");
            if (mRecordVoiceService != null) {
                mRecordVoiceService.stopPlayVoiceAnimation();
                if (imageView != null) {
                    mRecordVoiceService.setPlayImageView(imageView);
                }
                if (imageBgVoice != null) {
                    mRecordVoiceService.setIvBgVoice(imageBgVoice);
                }
                mRecordVoiceService.play(voiceMessageBody.getLocalUrl());
            }
        }
    }

    public void hideAllViews(){
        mBinding.viewInputTip.hideAllViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        //标记当前回话为已读
        try {
            EMClient.getInstance().chatManager().getConversation(chatUserId).markAllMessagesAsRead();
        }catch (Exception e){
        }
    }

    /**
     * 发送语音消息
     * @param filePath
     * @param length
     * @param chatUserId
     */
    protected void sendVoiceMessage(String filePath, int length, String chatUserId) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, chatUserId);
        send(message);
    }

    /**
     * 发送消息
     */
    private void sendTextMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        EMMessage message = EMMessage.createTxtSendMessage(msg, chatUserId);
        message.setChatType(EMMessage.ChatType.Chat);
        send(message);
    }

    /**
     * 发送文件
     * @param filePath
     */
    private void sendFileMessage(String filePath){
        EMMessage message = EMMessage.createFileSendMessage(filePath, chatUserId);
        send(message);
    }

    /**
     * 发送图片
     * @param imagePath
     */
    private void sendImageMessage(String imagePath){
        EMMessage message = EMMessage.createImageSendMessage(imagePath, true, chatUserId);
        send(message);
    }

    private void send(EMMessage message) {
        //消息扩展
        message.setAttribute("userHeadimgurlResource", MyApplication.getmUserInfo().getUser().getImgAddress());
        message.setAttribute("userNickname", MyApplication.getmUserInfo().getUser().getDisplayName());
        message.setAttribute("user", new Gson().toJson(MyApplication.getmUserInfo().getUser()));
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Chat chat = new Chat(message, ChatAdapter.MY_INFOMATION, MyApplication.getmUserInfo().getUser().getImgAddress()
                                , MyApplication.getmUserInfo().getUser().getDisplayName());
                        try {
                            if (message.getMsgTime() - mChatAdapter.getLastMessage().getMsgTime() > differenceTime) {
                                chat.setShowMessageTime(true);
                            }
                        }catch (Exception e){}
                        mChatAdapter.addItem(chat);
                        mBinding.rvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                        timer = new Timer();
                        timer.schedule(new AnimationTask(NOTIFY_CLOSE_KEYBOARD), 130);
                        PictureFileUtils.deleteCacheDirFile(ChatActivity.this);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    @Subscribe
    public void onReceiveMessage(BusEvent rxBusEvent){
        //破冰语
        if(rxBusEvent.getEvent() == RECEIVE_SEND_MESSAGE){
            String msg = (String)rxBusEvent.getEventObj();
            sendTextMessage(msg);
        }
    }

    private void initToolBar() {
        setTitle(username);
        mBinding.textTitle.setText(username);
        if (MyApplication.getmUserInfo() != null && MyApplication.getmUserInfo().getUser() != null) {
            EazyChatApi.getChatUserInfo(MyApplication.getmUserInfo().getUser(), this);
        }
    }

        //TODO 下拉刷新
//    @Override
//    public void onRefresh() {
//        if (mChatAdapter.getMsgId() != null) {
//            List<EMMessage> newlist = EazyChatApi.getMessageFromId(chatUserId, mChatAdapter.getMsgId(), 10);
//            mChatAdapter.notify(getChatList(newlist, chatUserId));
////            mBinding.rvChat.setRefreshing(false);
//        }
//
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                //TODO 点击输入法外
                closeInput();
                mBinding.viewInputTip.hideAllViews();
            }else{
                // 弹出输入法

            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] location = {0, 0};
            v.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            if ((event.getX() < left && event.getY() < top + v.getHeight()) || event.getY() < top ) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 关闭输入法
     */
    protected void closeInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mBinding.viewInputTip.getInputWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().post(new EMMessage(null));
        if(timer!=null){
            timer.cancel();
        }
        unbindService(conn);
    }


    protected void bindRecordService() {
        Intent intent = new Intent();
        intent.setClass(this, RecordVoiceService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 点击表情
     * @param emoji
     */
    @Override
    public void onEmojiClick(Emoji emoji) {
        if (emoji != null) {
            int index = mBinding.viewInputTip.getEtInput().getSelectionStart();
            Editable editable = mBinding.viewInputTip.getEtInput().getEditableText();
            if (index < 0) {
                editable.append(emoji.getContent());
            } else {
                editable.insert(index, emoji.getContent());
            }
        }
        displayTextView();
    }

    /**
     * 删除表情
     */
    @Override
    public void onEmojiDelete() {
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        mBinding.viewInputTip.getEtInput().onKeyDown(KeyEvent.KEYCODE_DEL, event);
        displayTextView();
    }

    /**
     * 展示表情
     */
    private void displayTextView() {
        try {
            EmojiUtil.handlerEmojiText(mBinding.viewInputTip.getEtInput(), mBinding.viewInputTip.getEtInput().getText().toString(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> localMediaList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia localMedia: localMediaList) {
                        sendImageMessage(localMedia.getPath());
                    }
                    break;
            }
        }
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final RecordVoiceService playService = ((RecordVoiceService.PlayBinder) service).getService();
            Log.e("onServiceConnected----", "onServiceConnected");
            mRecordVoiceService = playService;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    public class AnimationTask extends TimerTask {

        private int what;

        public AnimationTask(int what) {
            this.what = what;
        }

        @Override
        public void run() {
            Message message = new Message();
            message.what = what;
            handler.sendMessage(message);
        }
    }

    protected void getPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    EMMessageListener msgListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (list != null && list.size() != 0) {
                        EMMessage message = list.get(0);
                        if(!message.getFrom().equals(chatUserId)){
                            return;
                        }
                        if (message.getChatType() == EMMessage.ChatType.Chat) {
                            Chat chat = new Chat(message, ChatAdapter.OTHER_INFOMATION, imgUrl, username);
                            chat.setContext(ChatActivity.this);
                            if (message.getMsgTime() - mChatAdapter.getLastMessage().getMsgTime() > differenceTime) {
                                chat.setShowMessageTime(true);
                            }
                            mChatAdapter.addItem(chat);
                            mBinding.rvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                        }
                    }
                }
            });
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };

}
