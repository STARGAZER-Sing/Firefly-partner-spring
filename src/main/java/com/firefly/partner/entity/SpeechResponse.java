package com.firefly.partner.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpeechResponse {
    private String userInput;      // 用户语音识别结果
    private String aiResponse;      // AI原始回复（含动作描述）
    private String pureText;        // 纯文本回复
    private String audioPath;       // 生成的音频路径

    public SpeechResponse(String userInput, String aiResponse, String pureText, String audioPath) {
        this.userInput = userInput;
        this.aiResponse = aiResponse;
        this.pureText = pureText;
        this.audioPath = audioPath;
    }

}
