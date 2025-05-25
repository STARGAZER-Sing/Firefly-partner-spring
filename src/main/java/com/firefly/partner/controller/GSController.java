package com.firefly.partner.controller;

import com.firefly.partner.entity.gs.GSAudioRequest;
import com.firefly.partner.service.GSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gs")
public class GSController {

    @Autowired
    private GSService gsService;

    @PostMapping("/generate")
    public String generateAudio(@RequestBody GSAudioRequest request) {
        return gsService.generateAudio(request);
    }
}