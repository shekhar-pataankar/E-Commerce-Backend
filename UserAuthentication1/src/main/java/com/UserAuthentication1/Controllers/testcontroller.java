package com.UserAuthentication1.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testcontroller {

    @PostMapping("/test")
    public String test(){
        return "test";
    }
}
