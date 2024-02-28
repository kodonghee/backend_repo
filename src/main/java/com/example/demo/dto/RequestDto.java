package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RequestDto {
    private String prompt;
    private int max_tokens;
    private int n;
}