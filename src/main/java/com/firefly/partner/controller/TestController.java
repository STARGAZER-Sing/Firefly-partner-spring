package com.firefly.partner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@RestController
public class TestController {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @GetMapping("/api/debug/jackson-encoding")
    public Map<String, Object> checkJacksonEncoding() {
        Map<String, Object> result = new HashMap<>();

        List<HttpMessageConverter<?>> converters = handlerAdapter.getMessageConverters();

        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
                Charset charset = jacksonConverter.getDefaultCharset();
                ObjectMapper mapper = jacksonConverter.getObjectMapper();

                result.put("converter", jacksonConverter.getClass().getName());
                result.put("defaultCharset", charset.name());
                result.put("objectMapper", mapper.getClass().getName());
            }
        }

        return result;
    }
}
