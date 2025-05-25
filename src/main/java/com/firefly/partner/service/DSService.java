package com.firefly.partner.service;

import com.firefly.partner.entity.ds.DSChatRequest;
import com.firefly.partner.entity.ds.DSChatResponse;

public interface DSService {
    DSChatResponse askQuestion(DSChatRequest request);
}