package com.firefly.partner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

@Getter
@Setter
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class AiResponse {
    // Getter方法
    private boolean hasResponse;
    private List<String> actions = new ArrayList<>();
    private String cleanText;

    // 解析原始AI回复
    public static AiResponse parse(String rawResponse) {
        AiResponse response = new AiResponse();

        // 1. 检查是否有有效回复
        response.hasResponse = rawResponse.startsWith("AI回复:");

        // 2. 提取括号内的动作
        Pattern actionPattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = actionPattern.matcher(rawResponse);
        while (matcher.find()) {
            response.actions.add(matcher.group(1));
        }

        // 3. 清理后的纯文本
        response.cleanText = rawResponse
                .replaceFirst("^AI回复:", "")
                .replaceAll("\\(.*?\\)", "")
                .trim();

        return response;
    }

}