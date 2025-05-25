package com.firefly.partner.service.impl;

import com.firefly.partner.service.PlayVoiceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

@Service
public class PlayVoiceServiceImpl implements PlayVoiceService {

    @Value("${gptsovits.output.path}")
    private String audioOutputPath;

    @Override
    public void processPlayVoice(){
        playAudioFile(audioOutputPath);
    }


    private void playAudioFile(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            System.out.println("正在播放音频: " + filePath);
            clip.start();

            // 等待播放结束
            while (!clip.isRunning()) Thread.sleep(10);
            while (clip.isRunning()) Thread.sleep(10);

            clip.close();
            audioStream.close();
        } catch (Exception e) {
            System.err.println("音频播放失败: " + e.getMessage());
        }
    }
}


