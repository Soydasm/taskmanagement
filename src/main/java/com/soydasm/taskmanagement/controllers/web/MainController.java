package com.soydasm.taskmanagement.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class MainController {


    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Test");
        model.addAttribute("projectname", "WebApp");

        return "index";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }


}
