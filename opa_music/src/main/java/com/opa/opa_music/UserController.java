package com.opa.opa_music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    // Redirect to the registration page
    @GetMapping("/register")
    public String register() {
        return ("register");
    }

    /// Redirect to the login page
    @GetMapping("/loginRedirect")
    public String login() {
        return ("login");
    }

    @GetMapping("/loginError")
    public String logError(Model model) {
        model.addAttribute("error", "Bad Credentials");
        // System.out.println("EREUR");
        return ("login");
    }

    // Registration Form Handling
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, Model model) {
        if (!userService.isUsernameAlreadyTake(user.getName())) {
            Integer id = userService.saveUser(user);
            String message = "User " + id + " created successfully";
            model.addAttribute("msg", message);
            return ("redirect:/");
        }
        return ("/register");
    }

}
