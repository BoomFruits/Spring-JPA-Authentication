package com.example.userauth.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping(value = {"/", "/home"}) //Ai cũng vào được url này
    public String homepage() {
        return "home"; // Trả về home.html
    }

    @GetMapping("/user/home")
    public String userHomePage() {
        return "home_user"; // Trả về home_user.html
    }

    @GetMapping("/admin/home")
    public String adminHomePage() {
        return "home_admin";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "custom_login";
    }
    @GetMapping("/user/info")
    public String homepage1(){
        return "home_user";
    }
}
