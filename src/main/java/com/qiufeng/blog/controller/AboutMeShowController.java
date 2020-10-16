package com.qiufeng.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutMeShowController {

    @GetMapping("/aboutme")
    public String aboutMe(){
        return "aboutme";
    }

}
