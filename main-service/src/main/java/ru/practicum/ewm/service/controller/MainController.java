package ru.practicum.ewm.service.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("da")
public class MainController {

    @Value("${server.port}")
    private String port;
    @GetMapping()
    public String test(){
        return "Main Service is running on port " + port;
    }
}
