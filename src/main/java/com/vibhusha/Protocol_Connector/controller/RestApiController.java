package com.vibhusha.Protocol_Connector.controller;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/data")
    public Map<String, String> getData() {
        return Map.of("key", "value", "anotherKey", "anotherValue");
    }

    @PostMapping("/data")
    public String postData(@RequestBody Map<String, String> data) {
        return "Received data: " + data.toString();
    }
}
