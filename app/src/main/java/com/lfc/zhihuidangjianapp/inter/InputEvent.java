package com.lfc.zhihuidangjianapp.inter;

/**
 * @date: 2019/4/1
 * @autror: guojian
 * @description:
 */
public interface InputEvent {


    /**
     * 切换表情
     */
    void showEmojiView();

    /**
     * 是否显示扩展功能窗口
     * @param isShowFunction
     */
    void showFunctionView(boolean isShowFunction);

    /**
     * 发送消息
     * @param message 消息体
     */
    void sendMessage(String message);

    /**
     * 发送语音
     * @param voicePath 语音路径
     * @param lenth 语音时长
     */
    void sendVoice(String voicePath, int lenth);


}
