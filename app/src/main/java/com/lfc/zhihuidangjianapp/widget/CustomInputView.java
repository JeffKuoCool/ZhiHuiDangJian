package com.lfc.zhihuidangjianapp.widget;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.inter.InputEvent;
import com.lfc.zhihuidangjianapp.ui.activity.chat.InputFunctionFragment;
import com.lfc.zhihuidangjianapp.utlis.InputUtil;
import com.tb.emoji.FaceFragment;

/**
 * @date: 2019/4/1
 * @autror: guojian
 * @description: 输入法控件
 */
public class CustomInputView extends LinearLayout implements View.OnClickListener {

    private View btnShowVoice, btnSendVoice, btnShowEmoji, btnSendMessage, btnShowFunction, viewEmoji;

    private Context mContext;

    private EditText etInput;

    private InputEvent mInputEvent;

    private BaseActivity baseActivity;

    private boolean isSendFunction = true;

    /**
     * 功能栏
     */
    private FrameLayout viewFunction;

    public CustomInputView(Context context) {
        super(context);
        init(context);
    }

    public CustomInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.input_tip, this);
        btnShowVoice = findViewById(R.id.iv_record);
        btnSendVoice = findViewById(R.id.tv_send_voice);
        btnShowEmoji = findViewById(R.id.iv_emotion);
        etInput = findViewById(R.id.et_input);
        btnSendMessage = findViewById(R.id.iv_add_input);
        btnShowFunction = findViewById(R.id.iv_add_function);
        viewFunction = findViewById(R.id.view_input_function);
        viewEmoji = findViewById(R.id.view_emoji);


        initView();
        setEvent();
    }

    private void initView() {
        etInput.setHintTextColor(getResources().getColor(R.color.white));
//        etInput.requestFocus();
        etInput.clearFocus();
        inputEditor(etInput);
    }

    private void setEvent() {
        btnShowVoice.setOnClickListener(this);
        btnShowEmoji.setOnClickListener(this);
        btnSendMessage.setOnClickListener(this);
        btnShowFunction.setOnClickListener(this);
        viewFunction.setOnClickListener(this);

        /**
         * 点击输入法关闭输入法功能项
         */
        etInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideAllViews();
                return false;
            }
        });

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isSendFunction) {
                    inputEditor(etInput);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 响应事件 all
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //是否显示发送语音按键
            case R.id.iv_record:
                btnShowVoice.setSelected(!btnShowVoice.isSelected());
                if (btnShowVoice.isSelected()) {
                    btnSendVoice.setVisibility(View.VISIBLE);
                    btnShowVoice.setBackground(getResources().getDrawable(R.mipmap.ic_enter_appreciation));
                    btnShowEmoji.setVisibility(View.GONE);
                } else {
                    btnSendVoice.setVisibility(View.GONE);
                    btnShowVoice.setBackground(getResources().getDrawable(R.mipmap.ic_voice_issue));
                    btnShowEmoji.setVisibility(View.VISIBLE);
                }
                break;

            //切换表情窗口
            case R.id.iv_emotion:
                if (mInputEvent != null) {
                    mInputEvent.showEmojiView();
                }
                viewEmoji.setVisibility(View.VISIBLE);
                viewFunction.setVisibility(View.GONE);
                InputUtil.hideKeyboard(btnShowEmoji);
                break;

            //发送消息
            case R.id.iv_add_input:
                String intput = etInput.getText().toString().trim();
                if (intput != null && !intput.isEmpty() && mInputEvent != null) {
                    mInputEvent.sendMessage(intput);
                    InputUtil.hideKeyboard(etInput);
                }
                etInput.setText("");
                break;

            //输入法功能扩展
            case R.id.iv_add_function:
                btnShowFunction.setSelected(!btnShowFunction.isSelected());
                if (btnShowFunction.isSelected()) {
                    viewFunction.setVisibility(View.VISIBLE);
                } else {
                    viewFunction.setVisibility(View.GONE);
                }
                if (mInputEvent != null) {
                    mInputEvent.showFunctionView(btnShowFunction.isSelected());
                }
                viewEmoji.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 关闭输入法扩展功能窗口
     */
    public void hideAllViews() {
        viewFunction.setVisibility(View.GONE);
        viewEmoji.setVisibility(View.GONE);
    }

    public EditText getEtInput() {
        return etInput;
    }

    /**
     * @param voiceRecordView
     */
    public void isSendVoiced(VoiceRecordView voiceRecordView) {
        isSendVoice(true);
        btnSendVoice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return voiceRecordView.onPressToSpeakBtnTouch(view, motionEvent, new VoiceRecordView.EaseVoiceRecorderCallback() {
                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        //TODO 发送语音
                        if (mInputEvent != null) {
                            mInputEvent.sendVoice(voiceFilePath, voiceTimeLength);
                        }
                    }
                });
            }
        });
    }

    /**
     * 设置hint属性
     * @param text
     */
    public void setHintText(String text){
        etInput.setHint("回复"+text+"：");
    }

    /**
     * 是否可以发送语音
     * @param isSendVoice 默认为true
     */
    public void isSendVoice(boolean isSendVoice) {
        if (isSendVoice) {
            btnShowVoice.setVisibility(View.VISIBLE);
        } else {
            btnShowVoice.setVisibility(View.GONE);
        }
    }

    /**
     * 是否可以发送扩展功能
     * @param isSendFunction
     */
    public void isSendFunction(boolean isSendFunction){
        this.isSendFunction = isSendFunction;
        if(isSendFunction){
            btnShowFunction.setVisibility(View.VISIBLE);
            btnSendMessage.setVisibility(View.GONE);
        }else{
            btnShowFunction.setVisibility(View.GONE);
            btnSendMessage.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 是否可以发送emoji表情
     * @param isSendEmoji 默认为true
     */
    public void isSendEmoji(boolean isSendEmoji) {
        if (isSendEmoji) {
            btnShowEmoji.setVisibility(View.VISIBLE);
        } else {
            btnShowEmoji.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化输入法及监听变化
     * @param editText
     */
    private void inputEditor(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            btnShowFunction.setVisibility(View.VISIBLE);
            btnSendMessage.setVisibility(View.INVISIBLE);
        } else {
            btnShowFunction.setVisibility(View.INVISIBLE);
            btnSendMessage.setVisibility(View.VISIBLE);
            editText.setSelection(editText.getText().toString().trim().length());
        }
    }

    public IBinder getInputWindowToken() {
        return etInput.getWindowToken();
    }

    public void addInputEvent(InputEvent inputEvent) {
        mInputEvent = inputEvent;
    }

    public void setActivityContext(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        FaceFragment faceFragment = FaceFragment.Instance();
        baseActivity.getSupportFragmentManager().beginTransaction().add(R.id.view_emoji, faceFragment).commit();

        baseActivity.getSupportFragmentManager().beginTransaction().add(R.id.view_input_function, new InputFunctionFragment()).commit();
    }

}
