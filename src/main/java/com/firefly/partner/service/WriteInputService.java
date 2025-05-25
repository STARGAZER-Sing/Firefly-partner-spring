package com.firefly.partner.service;

public interface WriteInputService {
    /**
     * 手动输入文本 → DeepSeek处理 → 语音生成
     * @param userInput 用户输入的文本
     * @return 生成的音频文件路径
     */
    String processTextToAudio(String userInput);
}