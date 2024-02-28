package com.example.demo.controller;

import com.example.demo.service.MountainService;
import com.example.demo.service.WeatherService;
import com.example.demo.service.impl.WeatherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MountainController {
    private final MountainService mountainService;

    public MountainController(MountainService mountainService) {
        this.mountainService = mountainService;
    }

    @GetMapping("/create/mountain")
    String createMountain(){
        return mountainService.createMountain();
    }
}
