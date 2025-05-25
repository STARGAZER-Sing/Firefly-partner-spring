package com.firefly.partner.controller;

import com.firefly.partner.entity.SpeechResponse;
import com.firefly.partner.service.SpeechInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/speech")
public class SpeechInputController {

    @Autowired
    private SpeechInputService speechInputService;

    // 接口分离
    // Step 1: 初始化语音识别模块
    @GetMapping("/init")
    public String initModel() {
        try {
            // 如果你有模型懒加载逻辑，可以在这里做 voskService.prepareModel()
            return "ready";
        } catch (Exception e) {
            return "error";
        }
    }

    // Step 2: 开始语音识别 + AI 回复 + 播放语音
    @GetMapping("/recognize")
    public SpeechResponse recognizeAndRespond() {
        return speechInputService.processVoiceToAudio();
    }
    // 一步到位
    @GetMapping("/process")
    public SpeechResponse  processFullFlow() {
        return speechInputService.processVoiceToAudio();
    }
}