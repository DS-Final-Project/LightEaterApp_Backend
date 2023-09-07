package com.example.LightEaterApp.Chat.controller;

import com.example.LightEaterApp.Chat.service.SolutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("solution")
public class SolutionController {
    @Autowired
    private SolutionService service;

    //@GetMapping
}
