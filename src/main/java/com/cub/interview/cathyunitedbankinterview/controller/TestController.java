package com.cub.interview.cathyunitedbankinterview.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
@Slf4j
public class TestController {

    @GetMapping("/helloWorld")
    public ResponseEntity<String> helloWorld() {
        log.info("helloWorld");
        return ResponseEntity.ok("Hello World");
    }
}
