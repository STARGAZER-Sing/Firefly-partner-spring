package com.firefly.partner.entity.ds;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DSChatResponse {

    private List<DSChoice> choices;

    @Setter
    @Getter
    public static class DSChoice {

        private DSMessage message;

    }
}