package com.opa.opa_music;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
    @Getter @Setter
    private String token;
    @Getter @Setter
    private String type = "Bearer";
    private Integer id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String authenticationString;

    public JwtResponse(String accessToken, Integer id, String username, String email){
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public JwtResponse(String accessToken, String authentication){
        this.token = accessToken;
        this.authenticationString = authentication;
    }
}
