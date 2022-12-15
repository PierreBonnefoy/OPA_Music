package com.opa.opa_music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/*
 * Class wich implements the Controller for the Authentication system for the Thymeleaf client.
 */
@Controller
public class UserController {
    //Important imports.
    @Autowired
    private IUserService userService;

    // Redirect to the registration page
    @GetMapping("/register")
    public String register() {
        return ("register");
    }

    /// Redirect to the login page + login done.
    @GetMapping("/loginRedirect")
    public String login() {
        return ("login");
    }

    //Redirect to the loggin Error page (Not fully finished).
    @GetMapping("/loginError")
    public String logError(Model model) {
        model.addAttribute("error", "Bad Credentials");
        return ("login");
    }

    // Registration Form Handling
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, Model model) {
        //Verification of the unicity of the username in the database.
        if (!userService.isUsernameAlreadyTake(user.getName())) {
            //Save of the user into the database.
            Integer id = userService.saveUser(user);
            String message = "User " + id + " created successfully";

            //Response to the Thymeleaf client.
            model.addAttribute("msg", message);
            return ("redirect:/");
        }
        //If error redirect to register.
        return ("/register");
    }

}
