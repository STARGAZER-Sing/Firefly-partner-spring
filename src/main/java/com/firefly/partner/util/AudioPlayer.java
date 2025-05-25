package com.firefly.partner.util;

import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer {

    public static void play(String filePath) {
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // 等待播放结束
            while (clip.isRunning()) {
                Thread.sleep(100);
            }

            clip.close();
        } catch (Exception e) {
            throw new RuntimeException("音频播放失败", e);
        }
    }
}