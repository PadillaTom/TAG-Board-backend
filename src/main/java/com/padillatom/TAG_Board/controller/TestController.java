package com.padillatom.TAG_Board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/")
    public String testAll() {
        return "Bienvenido - ANY";
    }

    @GetMapping("/user")
    public String testUser() {
        return "Bienvenido - ROLE_USER";
    }

    @GetMapping("/admin")
    public String testAdmin() {
        return "Bienvenido - ROLE_ADMIN";
    }
}
