package com.yzn.gatewayserver.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {


    @GetMapping("/contact-support")
    public Mono<String> contactSupport() {
        return Mono.just("An error occurred. Please contact support at support@bankingapp.com");
    }


}
