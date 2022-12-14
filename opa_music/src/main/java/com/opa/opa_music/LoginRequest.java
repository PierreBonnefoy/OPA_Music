package com.opa.opa_music;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;


public class LoginRequest {
    @NotBlank @Getter @Setter
    private String username;

    @NotBlank @Getter @Setter
    private String password;
}
