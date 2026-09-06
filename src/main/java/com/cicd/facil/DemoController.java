package com.cicd.facil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RequestMapping("/v1/user")
@RestController
public class DemoController {

    private final DemoService service;

    public DemoController(DemoService service) {
        this.service = service;
    }

    @GetMapping("/name")
    public String getUserName() {
        return service.getUserName();
    }

    @GetMapping("/lastlogin")
    public LocalDateTime getUserLastLogin() {
        return LocalDateTime.now();
    }

}
