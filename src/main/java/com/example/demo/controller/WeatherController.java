package com.example.demo.controller;

import com.example.demo.service.WeatherService;
import com.example.demo.service.impl.WeatherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/create/weather")
    Map<String, Object> createWeather(@RequestParam Float latitude, @RequestParam Float longtitude){
        return weatherService.createWeather(latitude, longtitude);
    }
}
