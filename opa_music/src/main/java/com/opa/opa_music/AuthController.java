package com.opa.opa_music;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
/*
 * Class wich is implementing the controller to generate the REST API for Authentication in the Vue JS client.
 */
public class AuthController {
    //Imports of the usefull repositories
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    //Method wich handle the login requests of the VueJS client
    @PostMapping(value ="/signin",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){

        //Creation of the Authentication
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

       
        // Setting up the authentication in the Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //Generation of the JWT authentication token
        String jwt = jwtUtils.generateJwtToken(authentication);

        //Recuperation of the User Details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        //Return the response
        return ResponseEntity.ok(new JwtResponse(
                                jwt, 
                                userDetails.getId(),
                                userDetails.getUsername(),
                                userDetails.getEmail()));
    }
}
