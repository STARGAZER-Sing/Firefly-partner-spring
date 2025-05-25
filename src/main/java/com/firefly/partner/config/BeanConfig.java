package com.firefly.partner.config;

import com.firefly.partner.controller.UserController;
import com.firefly.partner.mapper.userMapper;
import com.firefly.partner.service.*;
import com.firefly.partner.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public DSService dsService() {
        return new DSServiceImpl();
    }

    @Bean
    public VoskService voskService() {
        return new VoskServiceImpl();
    }

    @Bean
    public GSService gsService() {
        return new GSServiceImpl();
    }

    @Bean
    public SpeechInputService speechInputService() {
        return new SpeechInputServiceImpl();
    }

    @Bean
    public PlayVoiceService playVoiceService(){ return new PlayVoiceServiceImpl(); };

}