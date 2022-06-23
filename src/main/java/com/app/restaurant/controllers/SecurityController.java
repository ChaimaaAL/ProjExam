package com.app.restaurant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class SecurityController {
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("error", "");
        return "security/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("error", "Informations de connexion invalide");
        return "security/loginForm";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {
        model.addAttribute("error", "Vous n'êtes pas autorisé à cette page");
        return "security/accessDeniedPage";
    }
}
