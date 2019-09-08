package com.lfc.zhihuidangjianapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.utlis.CommonUtils;
import com.lfc.zhihuidangjianapp.utlis.record.EMError;
import com.lfc.zhihuidangjianapp.utlis.record.VoiceRecorder;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author: guojian
 */
public class VoiceRecordView extends RelativeLayout {

    private Context mContext;
    protected VoiceRecorder voiceRecorder;
    protected PowerManager.WakeLock wakeLock;

    protected String release_to_cancel = "";
    protected String move_up_to_cancel = "";
    private Timer timer;
    private int countDown;
    private TextView tvCountDown, tvButton;
    private View ivCancel;
    private View parent;
    private int[] micImages = {R.mipmap.img_ripple1,
            R.mipmap.img_ripple2,
            R.mipmap.img_ripple3,
            R.mipmap.img_ripple4,
            R.mipmap.img_ripple5};

    private Handler micImageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            // TODO 渲染界面
            parent.setBackground(getResources().getDrawable(micImages[msg.what % 5]));
            if (msg.what != 0 && msg.what % 5 == 0) {
                tvCountDown.setText(getTime(msg.what / 5));
            }
        }
    };

    public interface EaseVoiceRecorderCallback {
        /**
         * on voice record complete
         *
         * @param voiceFilePath   录音完毕后的文件路径
         * @param voiceTimeLength 录音时长
         */
        void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength);
    }


    public VoiceRecordView(Context context) {
        super(context);
        initView(context);
    }

    public VoiceRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VoiceRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public VoiceRecordView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.voice_record, this);
        tvCountDown = findViewById(R.id.tv_countdowm);
        ivCancel = findViewById(R.id.iv_cancel);
        tvButton = findViewById(R.id.tv_buttom);
        parent = findViewById(R.id.parent);
        voiceRecorder = new VoiceRecorder(micImageHandler);

        wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK, "voice");
    }

    /**
     * on speak button touched
     *
     * @param v
     * @param event
     */
    public boolean onPressToSpeakBtnTouch(View v, MotionEvent event, EaseVoiceRecorderCallback recorderCallback) {
        TextView tvPress = (TextView) v;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tvPress.setText("松开发送");
                try {
                    /**
                     * 该处展示不处理
                     */
                  /*  if (VoicePlayClickListener.isPlaying)
                        VoicePlayClickListener.currentPlayListener.stopPlayVoice();*/
                    v.setPressed(true);
                    startRecording();
                } catch (Exception e) {
                    v.setPressed(false);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() < 0) {
                    showReleaseToCancelHint();
                } else {
                    showMoveUpToCancelHint();
                }
                return true;
            case MotionEvent.ACTION_UP:
                tvPress.setText("按住说话");
                stopTimer();
                v.setPressed(false);
                if (event.getY() < 0) {
                    // discard the recorded audio.
                    discardRecording();
                } else {
                    // stop recording and send voice file
                    try {
                        int length = stopRecoding();
                        if (length > 0) {
                            if (recorderCallback != null) {
                                recorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                            }
                        } else if (length == EMError.FILE_INVALID) {
                            Toast.makeText(mContext, "没有权限", Toast.LENGTH_SHORT).show();
                        } else {
                            //时间太短
//                            Toast.makeText(context, R.string.The_recording_time_is_too_short, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Toast.makeText(context, R.string.send_failure_please, Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            default:
                discardRecording();
                return false;
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    class RecordTask extends TimerTask {
        public void run() {
            countDown++;
            Message msg = new Message();
            msg.what = countDown;
            micImageHandler.sendMessage(msg);
        }
    }

    public void startRecording() {
        countDown = 0;
        tvCountDown.setText(getTime(countDown));
        if (!CommonUtils.isSdcardExist()) {
            Toast.makeText(mContext, "无SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            wakeLock.acquire();
            this.setVisibility(View.VISIBLE);
            voiceRecorder.startRecording(mContext);
            timer = new Timer();
            timer.schedule(new RecordTask(), 0, 200);
        } catch (Exception e) {
            e.printStackTrace();
            if (wakeLock.isHeld())
                wakeLock.release();
            if (voiceRecorder != null)
                voiceRecorder.discardRecording();
            this.setVisibility(View.INVISIBLE);
            Toast.makeText(mContext, "录音失败", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void showReleaseToCancelHint() {
        tvButton.setText("松开手指，取消发送");
        ivCancel.setVisibility(View.VISIBLE);
    }

    public void showMoveUpToCancelHint() {
        tvButton.setText("手指上划，取消发送");
        ivCancel.setVisibility(View.INVISIBLE);
    }

    public void discardRecording() {
        if (wakeLock.isHeld())
            wakeLock.release();
        try {
            // stop recording
            if (voiceRecorder.isRecording()) {
                voiceRecorder.discardRecording();
                this.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }
    }

    public int stopRecoding() {
        this.setVisibility(View.INVISIBLE);
        if (wakeLock.isHeld())
            wakeLock.release();
        return voiceRecorder.stopRecoding();
    }

    public String getVoiceFilePath() {
        return voiceRecorder.getVoiceFilePath();
    }

    public String getVoiceFileName() {
        return voiceRecorder.getVoiceFileName();
    }

    public boolean isRecording() {
        return voiceRecorder.isRecording();
    }

    /**
     * 自定义语音命名
     *
     * @param isTrue true为自定义，false为默认命名（时间戳）
     * @param name
     */
    public void setCustomNamingFile(boolean isTrue, String name) {
        voiceRecorder.isCustomNamingFile(isTrue, name);
    }

    /**
     * 目前需要传入15张帧动画png
     *
     * @param animationDrawable
     */
    public void setDrawableAnimation(Drawable[] animationDrawable) {
//        micImages = null;
//        this.micImages = animationDrawable;
    }


    /**
     * 设置按下显示的提示
     *
     * @param releaseToCancelHint
     */
    public void setShowReleaseToCancelHint(String releaseToCancelHint) {
        this.release_to_cancel = releaseToCancelHint;
    }

    /**
     * 设置手指向上移动显示的提示语
     *
     * @param moveUpToCancelHint
     */
    public void setShowMoveUpToCancelHint(String moveUpToCancelHint) {
        this.move_up_to_cancel = moveUpToCancelHint;
    }

    private static String getTime(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }

}
