package org.hotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("/home_page")
    public String getHomePage(){
        return "home_page";
    }

    @GetMapping("/personal_page")
    public String getPersonalPage(){
        return "personal_page";
    }

}
