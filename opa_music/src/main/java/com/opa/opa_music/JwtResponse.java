package com.opa.opa_music;

import lombok.Getter;
import lombok.Setter;

/*
 * Class wich represent the template of a JWT Response.
 */
public class JwtResponse {
    //Parameters of the request.
    @Getter @Setter
    private String token;
    @Getter @Setter
    private String type = "Bearer";
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String authenticationString;

    //Constructor
    public JwtResponse(String accessToken, Integer id, String username, String email){
        this.token = accessToken;
        this.username = username;
        this.email = email;
    }

    //Constructor
    public JwtResponse(String accessToken, String authentication){
        this.token = accessToken;
        this.authenticationString = authentication;
    }
}
