package com.lfc.zhihuidangjianapp.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.lfc.zhihuidangjianapp.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 后台播放语音
 */
public class RecordVoiceService extends Service implements MediaPlayer.OnCompletionListener {
    private static final String TAG = "Service";

    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PLAYING = 2;
    private static final int STATE_PAUSE = 3;

    private MediaPlayer mPlayer = new MediaPlayer();

    private int mPlayState = STATE_IDLE;

    private String playUrl = "";
    private ImageView ivPlay, ivBgVoice;
    private int[] bgVoice = {R.mipmap.img_audio_track_black_1, R.mipmap.img_audio_track_black_2, R.mipmap.img_audio_track_black_3};

    private AnimationDrawable voiceAnimation = null;
    public static boolean isPlaying = false;
    private int countDown;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    RecordVoiceService.this.ivPlay.setBackground(getResources().getDrawable(R.mipmap.biz_video_list_play_icon_big));
                    break;
                case 1:
                    if (ivBgVoice != null) {
                        countDown++;
                        ivBgVoice.setBackgroundResource(bgVoice[countDown % bgVoice.length]);
                    }
                    break;
            }
        }
    };
    private Timer timer;

    class RecordTask extends TimerTask {
        public void run() {
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    }

    public void setPlayImageView(ImageView ivPlay) {
        this.ivPlay = ivPlay;
        this.ivPlay.setBackground(getResources().getDrawable(R.mipmap.button_voice_play));
    }

    public void setIvBgVoice(ImageView ivBgVoice) {
        this.ivBgVoice = ivBgVoice;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer.setOnCompletionListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("url")) {
            playUrl = intent.getStringExtra("url");
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e("onCompletion-->", "onCompletion 100%");
        stopPlayVoiceAnimation();
        quit();
    }

    public void play(String url) {
        if (isPlaying) {
            if (playUrl != null) {
                stopPlayVoiceAnimation();
            }
        }
        try {
            mPlayer.reset();
            mPlayer.setDataSource(url);
            mPlayer.prepareAsync();
            mPlayState = STATE_PREPARING;
            mPlayer.setOnPreparedListener(mPreparedListener);
            mPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            isPlaying = true;
            playUrl = url;
            if (this.ivPlay != null) {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
            countDown = 0;
            timer = new Timer();
            timer.schedule(new RecordTask(), 0, 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlayVoiceAnimation() {
        if (voiceAnimation != null && voiceAnimation.isRunning()) {
            voiceAnimation.stop();
        }
        isPlaying = false;
        playUrl = null;
        if (this.ivPlay != null) {
            this.ivPlay.setBackground(getResources().getDrawable(R.mipmap.button_voice_play));
        }
        if (timer != null) {
            timer.cancel();
            if(ivBgVoice!=null){
                ivBgVoice.setBackgroundResource(R.mipmap.img_audio_track_black);
            }
        }
    }

    public void stopPlayVoiceAnimation2() {
        if (voiceAnimation != null) {
            voiceAnimation.stop();
        }
        if (isPlaying) {
            stopPlaying();
        }
        isPlaying = false;
        playUrl = null;
    }


    private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (isPreparing()) {
                start();
            }
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            Log.e("onBufferingUpdate--->", percent + "%");
        }
    };


    void start() {
        if (!isPreparing() && !isPausing()) {
            return;
        }

        mPlayer.start();
        mPlayState = STATE_PLAYING;
    }

    public void stopPlaying() {
        // stop play voice
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }


    public void stop() {
        if (isIdle()) {
            return;
        }

        mPlayer.reset();
        mPlayState = STATE_IDLE;
    }


    public boolean isPlaying() {
        return mPlayState == STATE_PLAYING;
    }

    public boolean isPausing() {
        return mPlayState == STATE_PAUSE;
    }

    public boolean isPreparing() {
        return mPlayState == STATE_PREPARING;
    }

    public boolean isIdle() {
        return mPlayState == STATE_IDLE;
    }


    @Override
    public void onDestroy() {
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        super.onDestroy();
        Log.i(TAG, "onDestroy: " + getClass().getSimpleName());
    }

    public void quit() {
        stop();
        stopSelf();
    }

    public class PlayBinder extends Binder {
        public RecordVoiceService getService() {
            return RecordVoiceService.this;
        }
    }
}
