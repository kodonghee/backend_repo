package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WeatherService {

    @Value("${openweathermap.key}")
    private String apiKey;
    public void createWeather(LocalDate date, String text){
        getWeatherString();
    }

    private String getWeatherString(){
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=" + apiKey;
        System.out.println(apiUrl);
        return "";

    }
}
