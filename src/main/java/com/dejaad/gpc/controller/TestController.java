package com.dejaad.gpc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("api/v1/test")
    public String test() {
        return "HUHUHU";
    }

}
