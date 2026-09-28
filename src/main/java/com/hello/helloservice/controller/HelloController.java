package com.hello.helloservice.controller;

import com.hello.helloservice.dto.MessageResponse;
import com.hello.helloservice.service.HelloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<MessageResponse> getGreeting(@RequestParam(name = "name", required = false) String name) {
        String greeting = helloService.generateGreeting(name);
        return ResponseEntity.ok(new MessageResponse(greeting));
    }
}
