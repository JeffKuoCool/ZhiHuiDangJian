package com.lfc.zhihuidangjianapp.ui.activity.items;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.Constants;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.databinding.ItemChatBinding;
import com.lfc.zhihuidangjianapp.event.BusEvent;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.ChatAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.model.Chat;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;
import com.lfc.zhihuidangjianapp.utlis.CommonUtils;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.tb.emoji.EmojiUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import cn.nekocode.items.view.RecyclerViewItemView;


/**
 * @date: 2018/8/7
 * @autror: guojian
 * @description:
 */

public class ChatItemView extends RecyclerViewItemView<Chat> {

    private ItemChatBinding mBinding;

    private BaseActivity mContext;

    @NonNull
    @Override
    public View onCreateItemView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_chat, parent, false);
        return mBinding.getRoot();
    }

    public ChatItemView setContext(BaseActivity context) {
        mContext = context;
        return this;
    }

    @Override
    public void onBindData(@NonNull Chat data) {
        if (data == null) {
            return;
        }
        try {
            if (data.getType() == ChatAdapter.MY_INFOMATION) {
                setMyView(data);
            } else {
                setOtherView(data);
            }
        } catch (Exception e) {
            Log.e("ChatItemView", e.getMessage());
        }
        if (data.isShowMessageTime()) {
            mBinding.tvTime.setVisibility(View.VISIBLE);
            mBinding.tvTime.setText(CommonUtils.timeStamp2Date(data.getMessage().getMsgTime() + "", ""));

        } else {
            mBinding.tvTime.setVisibility(View.GONE);
        }
    }

    /**
     * 我发出的消息
     *
     * @param chat
     */
    private void setMyView(Chat chat) throws IOException {
        mBinding.viewMe.getRoot().setVisibility(View.VISIBLE);
        mBinding.viewOther.getRoot().setVisibility(View.GONE);
        if (chat.getMessage().getBody() instanceof EMTextMessageBody) {
            mBinding.viewMe.tvMessage.setVisibility(View.VISIBLE);
            mBinding.viewMe.viewVoice.setVisibility(View.GONE);
            mBinding.viewMe.ivPicture.setVisibility(View.GONE);
            EmojiUtil.handlerEmojiText(mBinding.viewMe.tvMessage, ((EMTextMessageBody) chat.getMessage().getBody()).getMessage(), MyApplication.getAppContext());
        } else if (chat.getMessage().getBody() instanceof EMVoiceMessageBody) {
            mBinding.viewMe.tvMessage.setVisibility(View.GONE);
            mBinding.viewMe.viewVoice.setVisibility(View.VISIBLE);
            mBinding.viewMe.ivPicture.setVisibility(View.GONE);
            mBinding.viewMe.tvCount.setText(((EMVoiceMessageBody) chat.getMessage().getBody()).getLength() + "”");
            playVoiceEvent(mBinding.viewMe.viewVoice, (EMVoiceMessageBody) chat.getMessage().getBody(), mBinding.viewMe.ivPlay, mBinding.viewMe.imageBg);
        } else if (chat.getMessage().getBody() instanceof EMImageMessageBody) {
            mBinding.viewMe.tvMessage.setVisibility(View.GONE);
            mBinding.viewMe.viewVoice.setVisibility(View.GONE);
            mBinding.viewMe.ivPicture.setVisibility(View.VISIBLE);
            setPictrue((EMImageMessageBody) chat.getMessage().getBody(), mBinding.viewMe.ivPicture);
        }
        Glide.with(mBinding.viewMe.ivHead.getContext()).load(ApiConstant.ROOT_URL+MyApplication.getmUserInfo().getUser().getImgAddress()).into(mBinding.viewMe.ivHead);
    }

    /**
     * 别人发出的消息
     *
     * @param chat
     */
    private void setOtherView(Chat chat) throws IOException {
        mBinding.viewMe.getRoot().setVisibility(View.GONE);
        mBinding.viewOther.getRoot().setVisibility(View.VISIBLE);
        if (chat.getMessage().getBody() instanceof EMTextMessageBody) {
            mBinding.viewOther.tvMessage.setVisibility(View.VISIBLE);
            mBinding.viewOther.viewVoice.setVisibility(View.GONE);
            EmojiUtil.handlerEmojiText(mBinding.viewOther.tvMessage, ((EMTextMessageBody) chat.getMessage().getBody()).getMessage(), MyApplication.getAppContext());
        } else if (chat.getMessage().getBody() instanceof EMVoiceMessageBody) {
            mBinding.viewOther.tvMessage.setVisibility(View.GONE);
            mBinding.viewOther.viewVoice.setVisibility(View.VISIBLE);
            mBinding.viewOther.tvCount.setText(((EMVoiceMessageBody) chat.getMessage().getBody()).getLength() + "”");
            playVoiceEvent(mBinding.viewOther.viewVoice, (EMVoiceMessageBody) chat.getMessage().getBody(), mBinding.viewOther.ivPlay, mBinding.viewOther.imageBg);
        } else if (chat.getMessage().getBody() instanceof EMImageMessageBody) {
            mBinding.viewOther.tvMessage.setVisibility(View.GONE);
            mBinding.viewOther.viewVoice.setVisibility(View.GONE);
            mBinding.viewOther.ivPicture.setVisibility(View.VISIBLE);
            setPictrue((EMImageMessageBody) chat.getMessage().getBody(), mBinding.viewOther.ivPicture);
        }
        try {
            String userHeadimgurlResource = chat.getMessage().getStringAttribute("userHeadimgurlResource");
            String userNickname = chat.getMessage().getStringAttribute("userNickname");
            JSONObject jsonObject = chat.getMessage().getJSONObjectAttribute("user");
            User user = new Gson().fromJson(jsonObject.toString(), User.class);
            Glide.with(mBinding.viewOther.ivHead.getContext()).load(ApiConstant.ROOT_URL+MyApplication.getmUserInfo().getUser().getImgAddress()).into(mBinding.viewOther.ivHead);
            mBinding.viewOther.ivHead.setOnClickListener(head -> {
                if (chat.getContext() != null && user != null) {
                    //TODO 个人中心
//                    chat.getContext().getActivityRouter().gotoPersonalHomePage(chat.getContext(), user.getUserId());
                }
            });
        } catch (HyphenateException e) {
            e.printStackTrace();
        }

    }

    private void setPictrue(EMImageMessageBody emFileMessageBody, ImageView simpleDraweeView) {
        String url = emFileMessageBody.getRemoteUrl();
        if (mContext != null && url != null) {
            //TODO 查看大图
//            simpleDraweeView.setOnClickListener(picture -> mContext.getActivityRouter().gotoLargePicture(mContext, url));
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setOldController(simpleDraweeView.getController())
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        setImageViewSize(simpleDraweeView, imageInfo.getWidth(), imageInfo.getHeight());
                    }

                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {

                    }

                    @Override
                    public void onRelease(String id) {

                    }
                })
                .setUri(Uri.parse(url))
                .build();
//        simpleDraweeView.setController(controller);



//        Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                int width = bitmap.getWidth();
//                int height = bitmap.getHeight();
//                setImageViewSize(simpleDraweeView, width, height);
//                simpleDraweeView.setImageURI(url);
//            }
//        });

    }

    private void setImageViewSize(ImageView view, int targetWidth, int targetHeight) {
        int width, height;
        if (targetWidth >= targetHeight) {
            width = DispalyUtil.dp2px(mContext, 140);
            height = width * targetHeight / targetWidth;
        } else {
            height = DispalyUtil.dp2px(mContext, 140);
            width = targetWidth * height / targetHeight;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private void playVoiceEvent(View v, EMVoiceMessageBody voiceMessageBody, ImageView imageView, ImageView imageBg) {
        v.setOnClickListener(voice -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("image", imageView);
            map.put("body", voiceMessageBody);
            map.put("imageBg", imageBg);
            //TODO 播放语音
            BusEvent rxBusEvent = new BusEvent(Constants.BUS_PLAY_VOICE, map);
            RxBus.get().post(rxBusEvent);
        });
    }

}
