package com.opa.opa_music;


import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.micrometer.core.instrument.util.IOUtils;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserService userService;
    //Returning all users
    @RequestMapping("/users")
    public List<User> fetchUsers(){
        
        return userRepository.findAll();
    }

    @RequestMapping("/register")
    public ResponseEntity<Integer> doRegister(@RequestBody User user){
        if (!userService.isUsernameAlreadyTake(user.getName())) {
            Integer id = userService.saveUser(user);
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(0, HttpStatus.CONFLICT);
    }

    /*@PostMapping("/register")
	@Transactional
	public ResponseEntity<User> register(@RequestBody User user) {
		
		Integer userSaved = userService.saveUser(user);
        System.out.println("TRUE");
 		return new ResponseEntity<User>(HttpStatus.CREATED);
 	}*/
}
