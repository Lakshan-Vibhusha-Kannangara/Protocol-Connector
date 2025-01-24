package com.vibhusha.Protocol_Connector.controller;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/polling")
@EnableAsync
public class PollingController {

    @GetMapping("/start")
    @Async
    public CompletableFuture<String> startPolling(@RequestParam int interval) throws InterruptedException {
        Thread.sleep(interval);
        return CompletableFuture.completedFuture("Polling completed with interval: " + interval + "ms");
    }
}
