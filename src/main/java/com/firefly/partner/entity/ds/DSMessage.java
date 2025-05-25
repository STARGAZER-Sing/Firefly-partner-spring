package com.firefly.partner.entity.ds;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DSMessage {
    // Getters and Setters
    private String role;
    private String content;

    public DSMessage() {}

    public DSMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

}