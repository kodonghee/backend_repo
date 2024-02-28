package com.example.demo.controller;

import com.example.demo.dto.RequestDto;
import com.example.demo.service.KoGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class KoGPTController {
    @Autowired
    private final KoGPTService KoGPTService;

    public KoGPTController(KoGPTService KoGPTService) {
        this.KoGPTService = KoGPTService;
    }

    @GetMapping(value = "/create/KoGPT")
    public String createKoGPT(RequestDto request){
        return KoGPTService.createKoGPT(request);
    }
}
