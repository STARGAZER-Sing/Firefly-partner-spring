package com.firefly.partner.controller;

import com.firefly.partner.entity.vosk.VoskRecognitionResult;
import com.firefly.partner.service.VoskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/vosk")
public class VoskController {

    @Autowired
    private VoskService voskService;

    //@GetMapping("/recognize")
    @PostMapping(value = "/recognize")
    public void recognize(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        response.getWriter().println(voskService.recognizeSpeech());
    }
}