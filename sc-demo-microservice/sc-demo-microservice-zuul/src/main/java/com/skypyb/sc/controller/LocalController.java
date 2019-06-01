package com.skypyb.sc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本地转发示例
 */
@RestController
@RequestMapping("/local")
public class LocalController {


    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
