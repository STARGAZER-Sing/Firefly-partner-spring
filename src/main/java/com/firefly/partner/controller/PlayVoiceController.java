package com.firefly.partner.controller;

import com.firefly.partner.service.PlayVoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/play")
public class PlayVoiceController {

    @Autowired
    private PlayVoiceService playVoiceService;

    @PostMapping("/voice")
    public String playVoice() {
        try {
            playVoiceService.processPlayVoice();
            return "音频播放成功";
        } catch (Exception e) {
            return "音频播放失败: " + e.getMessage();
        }
    }

}
