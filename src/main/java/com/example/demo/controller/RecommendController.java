package com.example.demo.controller;

import com.example.demo.service.RecommendService;
import com.example.demo.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RecommendController {
    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @PostMapping("/create/recommend")
    String createRecommend(@RequestParam String prompt, @RequestParam Integer max_tokens){
        return recommendService.createRecommend(prompt, max_tokens);
    }
}
