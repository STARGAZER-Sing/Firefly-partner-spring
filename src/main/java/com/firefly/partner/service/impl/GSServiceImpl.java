package com.firefly.partner.service.impl;

import com.firefly.partner.entity.gs.GSAudioRequest;
import com.firefly.partner.service.GSService;
import com.firefly.partner.util.AudioPlayer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GSServiceImpl implements GSService {

    @Value("${gptsovits.api.url}")
    private String apiUrl;

    @Value("${gptsovits.output.path}")
    private String outputPath;

    @Override
    public String generateAudio(GSAudioRequest request) {
        try {
            // 1. 设置参考音频
            setReference(request.getReferenceAudioPath(), request.getReferenceText());

            // 2. 生成语音
            generateSpeech(request.getTargetText());

            // 3. 播放音频
            AudioPlayer.play(outputPath);

            return outputPath;
        } catch (Exception e) {
            e.printStackTrace();  // 打印完整堆栈
            throw new RuntimeException("音频生成失败", e);
        }
    }

    private void setReference(String refAudioPath, String refText) throws Exception {
        String jsonBody = String.format(
                "{\"refer_wav_path\":\"%s\",\"prompt_text\":\"%s\",\"prompt_language\":\"zh\"}",
                refAudioPath.replace("\\", "/"),
                refText
        );

        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl + "/change_refer").openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("设置参考音频失败: " + conn.getResponseCode());
        }
    }

    private void generateSpeech(String text) throws Exception {
        String url = String.format(
                "%s?text=%s&text_language=zh",
                apiUrl,
                URLEncoder.encode(text, StandardCharsets.UTF_8)
        );

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(30000);

        try (InputStream is = conn.getInputStream();
             FileOutputStream fos = new FileOutputStream(outputPath);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HTTP错误: " + conn.getResponseCode());
        }
    }
}