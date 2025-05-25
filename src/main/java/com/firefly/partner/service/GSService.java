package com.firefly.partner.service;

import com.firefly.partner.entity.gs.GSAudioRequest;

public interface GSService {
    String generateAudio(GSAudioRequest request);
}