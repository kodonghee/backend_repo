package com.example.demo.service.impl;

//import com.sun.org.apache.xpath.internal.operations.String;
import com.example.demo.service.WeatherService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${openweathermap.key}")
    private String apiKey;
    @Override
    public ArrayList<Map<String, Object>> createWeather(Float latitude, Float longtitude){
        // weather map에서 날씨 데이터 가져옴
        String weatherData = getWeatherString(latitude, longtitude);

        // 날씨 json 파싱
        ArrayList<Map<String, Object>> parseWeather = parseWeather(weatherData);
        System.out.println(parseWeather);

        // 파싱 데이터 db 저장
        return parseWeather;
    }

    private String getWeatherString(Float latitude, Float longtitude){
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longtitude + "&appid=" + apiKey;
        try{

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if(responseCode == 200){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e){
            return "failed to get response";
        }
    }

    private ArrayList<Map<String, Object>> parseWeather(String jsonString){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch(Exception e){
            throw new RuntimeException(e);
        }

        ArrayList<Map<String, Object>> resultMap = new ArrayList<>();

        JSONArray listData = (JSONArray) jsonObject.get("list");

        for (int i =0; i < listData.size(); i++){
            JSONObject temp = (JSONObject) listData.get(i);
            Map<String, Object> resultdMap = new HashMap<>();
            resultdMap.put("id", i);
            resultdMap.put("main", temp.get("main"));
            resultdMap.put("weather", temp.get("weather"));
            resultdMap.put("dt_txt", temp.get("dt_txt"));
            resultMap.add(resultdMap);
            //resultMap.put(i + "_dt_txt", temp.get("dt_txt"));
        }

        /*
        resultMap.put("main", mainData.get("main"));
        resultMap.put("main", mainData.get("main"));
        //JSONObject weatherData = (JSONObject) jsonObject.get("weather");
        //resultMap.put("main", weatherData.get("main"));
        //resultMap.put("icon", weatherData.get("icon"));
        */

        return resultMap;
    }

}
