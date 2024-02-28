package com.example.demo.service.impl;

import com.example.demo.service.MountainService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class MountainServiceImpl implements MountainService {

    //@Value("${openweathermap.key}")
    //private String apiKey;
    @Override
    public String createMountain(){
        // 오름 데이터 가져옴
        String mountainData = getMountainString();

        // 날씨 json 파싱
        //Map<String, Object> parseMountain = parseMountain(mountainData);
        //System.out.println(parseMountain);

        // 파싱 데이터 db 저장
        return mountainData;
    }

    private String getMountainString(){
        String apiUrl = "https://gis.jeju.go.kr/rest/JejuOleumVRImg/getOleumADetailList";
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

    private Map<String, Object> parseMountain(String jsonString){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch(Exception e){
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();

        JSONArray listData = (JSONArray) jsonObject.get("resultSummary");

        for (int i =0; i < listData.size(); i++){
            JSONObject temp = (JSONObject) listData.get(i);
            resultMap.put(i + "번째 오름", temp.get("oleumKname"));
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
