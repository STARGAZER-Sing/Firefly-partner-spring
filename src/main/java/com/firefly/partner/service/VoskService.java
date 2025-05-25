package com.firefly.partner.service;

import com.firefly.partner.entity.vosk.VoskRecognitionResult;

public interface VoskService {
    VoskRecognitionResult recognizeSpeech();
}