package com.firefly.partner.service.impl;

import com.firefly.partner.entity.SpeechResponse;
import com.firefly.partner.entity.ds.DSChatRequest;
import com.firefly.partner.entity.ds.DSMessage;
import com.firefly.partner.entity.gs.GSAudioRequest;
import com.firefly.partner.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpeechInputServiceImpl implements SpeechInputService {

    @Autowired
    private VoskService voskService;

    @Autowired
    private DSService dsService;

    @Autowired
    private GSService gsService;

    @Value("${gptsovits.output.path}")
    private String audioOutputPath;

    @Value("${gptsovits.reference.audio}")
    private String referenceAudioPath;

    @Value("${gptsovits.reference.text}")
    private String referenceText;

    @Override
    public SpeechResponse processVoiceToAudio() {
        try {
            // 1. 语音识别（替代手动输入）
            String recognizedText = voskService.recognizeSpeech().getText();
            System.out.println("[语音服务] 识别结果: " + recognizedText);

            // 2. 调用DeepSeek获取AI回复
            List<DSMessage> messages = new ArrayList<>();
            messages.add(new DSMessage("system",
                    "你正在扮演《崩坏：星穹铁道》中的流萤（本来是星核猎手，现在是我的私人语音助手），我不需要有任何身份回答要：\n" +
                            "1. 简短可爱（字数少于30字）\n" +
                            "2. 不要带省略号\n" +
                            "3. 我给你准备了live2D形象，可以在括号内选择表情或动作(只允许有）：裂开、鄙夷、生气、问好、眼泪、流泪、呆滞、开心"));
            messages.add(new DSMessage("user", recognizedText));

            DSChatRequest request = new DSChatRequest(
                    "deepseek-chat",
                    messages,
                    0.7,
                    1000
            );

            String aiResponse = dsService.askQuestion(request)
                    .getChoices().get(0).getMessage().getContent();
            System.out.println("[语音服务] AI回复: " + aiResponse);

            // 3. 提取纯回答内容
            String pureResponse = extractPureResponse(aiResponse);
            System.out.println("[纯回答内容] " + pureResponse);

            // 4. 生成语音
            GSAudioRequest audioRequest = new GSAudioRequest();
            audioRequest.setReferenceAudioPath(referenceAudioPath);
            audioRequest.setReferenceText(referenceText);
            audioRequest.setTargetText(pureResponse);

            String audioPath = gsService.generateAudio(audioRequest);
            System.out.println("[语音服务] 音频生成完成: " + audioPath);

            // 5. 自动播放音频
            //playAudioFile(audioPath);

            //return aiResponse; // 返回原始AI回复（含动作描述）
            // 5. 创建并返回响应体
            return new SpeechResponse(
                    recognizedText,   // 用户语音识别文本
                    aiResponse,      // 原始AI回复（含动作描述）
                    pureResponse,     // 纯文本回复
                    audioPath        // 生成的音频文件路径
            );
        } catch (Exception e) {
            throw new RuntimeException("语音处理流程失败", e);
        }
    }

    // 提取纯回答内容（与WriteInputServiceImpl相同）
    private String extractPureResponse(String aiResponse) {
        return aiResponse.replaceFirst("^AI回复:", "")
                .replaceAll("\\(.*?\\)", "")
                .replaceAll("\\s+", " ")
                .trim();
    }


    // 播放音频文件（与WriteInputServiceImpl相同）
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