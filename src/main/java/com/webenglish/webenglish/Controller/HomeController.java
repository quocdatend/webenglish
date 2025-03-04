package com.webenglish.webenglish.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Home")
public class HomeController {
    @GetMapping("")
    public String Home(){
        return "Users/Home";
    }
}
