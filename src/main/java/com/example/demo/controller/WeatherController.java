package com.example.demo.controller;

import com.example.demo.service.WeatherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/create/weather")
    void createWeather(@RequestParam Float latitude, @RequestParam Float longtitude){
        weatherService.createWeather(latitude, longtitude);
    }
}
