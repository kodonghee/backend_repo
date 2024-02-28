package com.example.demo.service;

import java.util.Map;

public interface RecommendService {
    String createRecommend(String prompt, Integer max_tokens);
}
