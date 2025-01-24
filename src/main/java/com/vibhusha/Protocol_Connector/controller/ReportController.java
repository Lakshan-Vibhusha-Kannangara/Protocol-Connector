package com.vibhusha.Protocol_Connector.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @GetMapping("/report")
    public String generateReport() {
        return "Report generated successfully";
    }
}

