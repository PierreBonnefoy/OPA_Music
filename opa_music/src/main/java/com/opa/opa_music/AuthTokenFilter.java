package com.opa.opa_music;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.util.StringUtils;

/*
 * Class wich implements the Token Filter
 */
public class AuthTokenFilter extends OncePerRequestFilter {
    //Import of usefull Utils and repositories
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    // Method wich is verifying if the JWT login request is good
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //Verification of the integrity of the request.
            String jwt = parseJwt(request);

            //Verification of the validity of the JWT token.
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                //Recuperation of the username
                String username = jwtUtils.getUsernameFromJwtToken(jwt);

                //Recuperation of the User class corresponding to the username.
                UserDetails user = userDetailsService.loadUserByUsername(username);
                
                //Setting up the Authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //Catching eventual errors.
        catch(Exception e){
            logger.error("Cannot set User authentication", e);
        }

        filterChain.doFilter(request, response);
    }

    //Method wich is verifying the integrity of the request
    private String parseJwt(HttpServletRequest request){
        //Header of the POST request verification
        String headerAuth = request.getHeader("Authorization");

        //Verification that its a Bearer request.
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")){
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
    
}
