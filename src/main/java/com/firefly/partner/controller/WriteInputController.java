package com.firefly.partner.controller;

import com.firefly.partner.entity.TextInputRequest;
import com.firefly.partner.service.WriteInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/write")
public class WriteInputController {

    @Autowired
    private WriteInputService writeInputService;

    @PostMapping(value = "/process", consumes = "application/json")
    public String processWriteInput(@RequestBody TextInputRequest request) {
        return writeInputService.processTextToAudio(request.getText());
    }
}