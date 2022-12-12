package com.opa.opa_music.vueControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.opa.opa_music.Favorites;
import com.opa.opa_music.User;
import com.opa.opa_music.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/vue/api")
@CrossOrigin("http://localhost:3000/")
public class RESTController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> fetchUsers(){
        return userRepository.findAll();
    }
}
