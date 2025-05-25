package com.firefly.partner.entity.ds;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DSChatRequest {

    private String model;
    private List<DSMessage> messages;
    private double temperature;
    private int max_tokens;

    public DSChatRequest() {}



    public DSChatRequest(String model, List<DSMessage> messages, double temperature, int max_tokens) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }

}