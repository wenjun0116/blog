package com.qiufeng.blog.controller;

import com.qiufeng.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class ArchivesShowController {

    @Resource
    BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archivesMap",blogService.archiveBlog());

        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }

}
