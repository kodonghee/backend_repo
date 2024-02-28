package com.example.demo.service.impl;

//import com.sun.org.apache.xpath.internal.operations.String;
import com.example.demo.dto.RequestDto;
import com.example.demo.service.KoGPTService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class KoGPTServiceImpl implements KoGPTService {

    @Value("${openkakaogpt.key}")
    private String apiKey;
    @Override
    public String createKoGPT(RequestDto request){
        // weather map에서 날씨 데이터 가져옴
        String KoGPTData = getKoGPTString(request);

        // 날씨 json 파싱
        //Map<String, Object> parseKoGPT = parseKoGPT(KoGPTData);
        System.out.println(KoGPTData);

        // 파싱 데이터 db 저장
        return KoGPTData;
    }

    private String getKoGPTString(RequestDto request){
        String apiUrl = "https://api.kakaobrain.com/v1/inference/kogpt/generation";

        String prompt = request.getPrompt();
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("prompt", "제주 날씨 좋은 오름 찾아줘");
        params.add("max_tokens", 120);
        params.add("n", 2);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", apiKey);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(params, headers);

        try{
            RestTemplate rt = new RestTemplate();

            ResponseEntity<String> response = rt.exchange(
                    apiUrl, //{요청할 서버 주소}
                    HttpMethod.GET, //{요청할 방식}
                    entity, // {요청할 때 보낼 데이터}
                    String.class //{요청시 반환되는 데이터 타입}
            );
            /*URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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
            br.close();*/
            System.out.println(response);

            return response.toString();
        } catch (Exception e){
            e.printStackTrace();
            return "failed to get response";
        }
    }

    private Map<String, Object> parseKoGPT(String jsonString){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch(Exception e){
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();

        JSONArray listData = (JSONArray) jsonObject.get("list");

        for (int i =0; i < listData.size(); i++){
            JSONObject temp = (JSONObject) listData.get(i);
            resultMap.put(i + "번째 main", temp.get("main"));
            resultMap.put(i + "번째 weather", temp.get("weather"));
            resultMap.put(i + "번째 dt_txt", temp.get("dt_txt"));
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
