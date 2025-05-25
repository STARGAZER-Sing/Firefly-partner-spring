package com.firefly.partner.service.impl;

import com.firefly.partner.entity.ds.DSChatRequest;
import com.firefly.partner.entity.ds.DSChatResponse;
import com.firefly.partner.service.DSService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



@Service
public class DSServiceImpl implements DSService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Override
    public DSChatResponse askQuestion(DSChatRequest request) {
        try {
            String requestBodyJson = gson.toJson(request);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), DSChatResponse.class);
            } else {
                throw new RuntimeException("请求失败，状态码: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("请求异常", e);
        }
    }
}