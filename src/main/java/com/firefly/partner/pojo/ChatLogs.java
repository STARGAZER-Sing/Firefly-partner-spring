package com.firefly.partner.pojo;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


@lombok.Data
@AllArgsConstructor
@NoArgsConstructor

public class ChatLogs {
    private int chatID;
    private String sessionID;
    private String input;
    private String response;
    private String audio;
    private Integer latency;
    private Integer status;
    private Data createTime;
    private int UID;
}
