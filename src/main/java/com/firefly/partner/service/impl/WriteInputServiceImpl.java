package com.firefly.partner.service.impl;

import com.firefly.partner.entity.ds.DSChatRequest;
import com.firefly.partner.entity.ds.DSMessage;
import com.firefly.partner.entity.gs.GSAudioRequest;
import com.firefly.partner.service.DSService;
import com.firefly.partner.service.GSService;
import com.firefly.partner.service.WriteInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class WriteInputServiceImpl implements WriteInputService {

    @Autowired
    private DSService dsService;

    @Autowired
    private GSService gsService;

    @Value("${gptsovits.output.path}")  // 新增配置项
    private String audioOutputPath;

    @Value("${gptsovits.reference.audio}")
    private String referenceAudioPath;

    @Value("${gptsovits.reference.text}")
    private String referenceText;

    @Override
    public String processTextToAudio(String userInput) {
        try {
            // 1. 调用DeepSeek获取AI回复
            List<DSMessage> messages = new ArrayList<>();
            messages.add(new DSMessage("system",
                    "你正在扮演《崩坏：星穹铁道》中的流萤（本来是星核猎手，现在是我的私人语音助手），我不需要有任何身份回答要：\n" +
                            "1. 简短可爱（字数少于50字）\n" +
                            "2. 不要带省略号\n" +
                            "3. 我给你准备了live2D形象，可以在括号内选择表情或动作(只允许有）：裂开、鄙夷、生气、问好、眼泪、流泪、呆滞、开心"));
            messages.add(new DSMessage("user", userInput));

            DSChatRequest request = new DSChatRequest(
                    "deepseek-chat",
                    messages,
                    0.7,
                    1000
            );

            String aiResponse = dsService.askQuestion(request)
                    .getChoices().get(0).getMessage().getContent();

            System.out.println("[手动输入服务] AI回复: " + aiResponse);

            // 2. 提取纯回答内容
            String pureResponse = extractPureResponse(aiResponse);
            System.out.println("[纯回答内容] " + pureResponse);

            // 2. 生成语音
            GSAudioRequest audioRequest = new GSAudioRequest();
            audioRequest.setReferenceAudioPath(referenceAudioPath);
            audioRequest.setReferenceText(referenceText);
            audioRequest.setTargetText(pureResponse);

            String audioPath = gsService.generateAudio(audioRequest);
            System.out.println("[手动输入服务] 音频生成完成: " + audioPath);

            // 4. 自动播放音频
            //playAudioFile(audioPath);

            return aiResponse;

        } catch (Exception e) {
            throw new RuntimeException("文本处理流程失败", e);
        }

    }

    // 提取纯回答内容
    private String extractPureResponse(String aiResponse) {
        // 移除系统前缀（如果有）
        String response = aiResponse.replaceFirst("^AI回复:", "").trim();

        // 移除括号内的动作描述
        response = response.replaceAll("\\(.*?\\)", "");

        // 移除多余空格和换行
        return response.replaceAll("\\s+", " ").trim();
    }

    //  播放音频文件
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