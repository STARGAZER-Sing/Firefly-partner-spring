package com.firefly.partner.controller;

import com.firefly.partner.entity.ds.DSChatRequest;
import com.firefly.partner.entity.ds.DSChatResponse;
import com.firefly.partner.service.DSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ds")
public class DSController {

    @Autowired
    private DSService dsService;

    @PostMapping("/ask")
    public DSChatResponse askQuestion(@RequestBody DSChatRequest request) {
        return dsService.askQuestion(request);
    }
}