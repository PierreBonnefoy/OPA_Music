package com.opa.opa_music;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/*
 * Class wich implement the template of a login request.
 */
public class LoginRequest {
    @NotBlank @Getter @Setter
    private String username;

    @NotBlank @Getter @Setter
    private String password;
}
