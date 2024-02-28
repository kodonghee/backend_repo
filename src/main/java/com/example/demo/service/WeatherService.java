package com.example.demo.service;

import java.util.Map;

public interface WeatherService {
    Map<String, Object> createWeather(Float latitude, Float longtitude);
}
