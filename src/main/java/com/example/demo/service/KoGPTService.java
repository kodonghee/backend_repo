package com.example.demo.service;

import com.example.demo.dto.RequestDto;
import jdk.nashorn.internal.ir.RuntimeNode;

public interface KoGPTService {
    String createKoGPT(RequestDto request);
}
