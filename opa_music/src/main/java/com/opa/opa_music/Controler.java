package com.opa.opa_music;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Controler {
    @Inject
    public PersonRepository userRep;

    // Handle the home page of the web site
    @RequestMapping("/")
    public String home() {
        return "home";
    }

    // Handling the sing in redirection
    @GetMapping("/signIn")
    public String signIn() {
        return ("signIn");
    }

    // Handling the signup redirection
    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("newUser", new Person());
        return ("signUp");
    }

    // Handling the Sign Up procedure
    @PostMapping("/addNewUser")
    public String addNewUser(Person newUser) {
        userRep.save(newUser);
        return ("redirect:/");
    }
}
