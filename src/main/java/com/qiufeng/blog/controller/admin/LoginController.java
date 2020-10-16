package com.qiufeng.blog.controller.admin;

import com.qiufeng.blog.entity.User;
import com.qiufeng.blog.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping
    public String loginPage(){
       return  "admin/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("userName")String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verificationCode")String verificationCode,
                        HttpSession session,
                        RedirectAttributes attributes){
        if (verificationCode != null && session.getAttribute("RANDOMCODE_IN_SESSION").equals(verificationCode)){
            User user = userService.checkUser(userName,password);
            if (user != null){
                user.setPassword(null);
                session.setAttribute("user",user);
                return "admin/index";
            }else {
                attributes.addFlashAttribute("message","用户名或密码错误");
            }
        }else {
            attributes.addFlashAttribute("message","验证码错误");
        }
        return "redirect:/admin";
    }

    @GetMapping("loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
