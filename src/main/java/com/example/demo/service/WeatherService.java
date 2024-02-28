package com.example.demo.service;

import java.util.ArrayList;
import java.util.Map;

public interface WeatherService {
    ArrayList<Map<String, Object>> createWeather(Float latitude, Float longtitude);
}
