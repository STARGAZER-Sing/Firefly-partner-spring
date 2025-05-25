package com.firefly.partner.entity.gs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GSAudioRequest {

    private String referenceAudioPath;
    private String referenceText;
    private String targetText;

    // 必须添加的构造函数
    public GSAudioRequest() {}

    // 全参构造函数（如果报错则必须添加）
    public GSAudioRequest(String referenceAudioPath, String referenceText, String targetText) {
        this.referenceAudioPath = referenceAudioPath;
        this.referenceText = referenceText;
        this.targetText = targetText;
    }

}