package com.firefly.partner.service;

import com.firefly.partner.entity.SpeechResponse;

public interface SpeechInputService {
    /**
     * 完整流程：语音识别 → DeepSeek问答 → 语音生成
     * @return 生成的音频文件路径
     */
    SpeechResponse processVoiceToAudio();
}