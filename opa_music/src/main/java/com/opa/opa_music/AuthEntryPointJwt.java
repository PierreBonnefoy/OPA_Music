package com.opa.opa_music;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
/*
 * Class Wich implement the Entry Point of the Login JWT authentication system
 */
public class AuthEntryPointJwt implements AuthenticationEntryPoint{
    //Creation of the logger
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    //Method wich is verifying if you are authorized to access.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        logger.error("Unauthorized Error :{}", authenticationException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error : Unauthorized");
    }
}
