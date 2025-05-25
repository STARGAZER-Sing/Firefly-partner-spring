package com.firefly.partner.service.impl;


import com.firefly.partner.entity.vosk.VoskRecognitionResult;
import com.firefly.partner.service.VoskService;
import org.springframework.stereotype.Service;
import org.vosk.Model;
import org.vosk.Recognizer;
import javax.sound.sampled.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class VoskServiceImpl implements VoskService {

    private static final String modelPath = "E:\\Firefly-partner\\Firefly-partner-spring\\src\\main\\resources\\config\\vosk-model-cn-0.22\\vosk-model-cn-0.22";
    private static final int SAMPLE_RATE = 16000;

    @Override
    public VoskRecognitionResult recognizeSpeech() {
        try (Model model = new Model(modelPath);
             Recognizer recognizer = new Recognizer(model, SAMPLE_RATE);
             AudioInputStream audioStream = getConvertedAudioStream(getTargetFormat())) {

            System.out.println("开始语音识别... (说话即可)");

            String result = processAudioStream(recognizer, audioStream);
            return new VoskRecognitionResult(result);

        } catch (Exception e) {
            throw new RuntimeException("语音识别失败", e);
        }
    }


    private AudioFormat getTargetFormat() {
        return new AudioFormat(SAMPLE_RATE, 16, 1, true, false); // 16kHz, 16bit, mono, signed, little endian
    }

    private AudioInputStream getConvertedAudioStream(AudioFormat targetFormat) throws LineUnavailableException {

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, targetFormat);
        if (!AudioSystem.isLineSupported(info)) {
            throw new IllegalStateException("麦克风不支持该音频格式");
        }

        TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
        System.out.println("获取麦克风成功，准备打开...");
        line.open(targetFormat);
        System.out.println("麦克风已打开！");
        line.start();
        return new AudioInputStream(line);
    }


    private String processAudioStream(Recognizer recognizer, AudioInputStream stream) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;

        long startTime = System.currentTimeMillis();
        long timeoutMs = 10000;

        while ((bytesRead = stream.read(buffer)) != -1) {
            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                // 直接获取字符串结果
                String jsonResult = recognizer.getResult();

                // 调试用：打印原始字节（可选）
                byte[] rawBytes = jsonResult.getBytes(StandardCharsets.UTF_8);
                System.out.println("Raw JSON bytes: " + Arrays.toString(rawBytes));

                // 输出实际内容
                System.out.println("Raw JSON result: " + jsonResult);

                return jsonResult;
            }

            // 超时控制
            if (System.currentTimeMillis() - startTime > timeoutMs) {
                break;
            }
        }
        return null;
    }


}

