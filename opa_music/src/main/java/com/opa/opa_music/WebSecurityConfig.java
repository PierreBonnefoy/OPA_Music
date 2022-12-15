package com.opa.opa_music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * Class wich implements the Security Management of the system.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
        //Some Important Imports.
        @Autowired
        private UserDetailsService uds;

        @Autowired
        private BCryptPasswordEncoder encoder;

        //Definition of the entry point for JWT token authentication.
        @Autowired 
        private AuthEntryPointJwt unauthorizedHandler;

        //Definition of the Token filter.
        @Bean
        public AuthTokenFilter authentificationJwtTokenFilter(){
                return new AuthTokenFilter();
        }

        //Definition of the Authentication Manager
        @Bean
        protected AuthenticationManager authenficationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
                return authenticationConfiguration.getAuthenticationManager();
        }

        //Definition of the Authentication Provider.
        @Bean
        public DaoAuthenticationProvider authenticationProvider(){
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

                authProvider.setUserDetailsService(uds);
                authProvider.setPasswordEncoder(encoder);

                return authProvider;
        }

        //Main function for the security.
        @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
                http    //Disabling the CORS system.
                        .cors().disable()
                        //Definition of the entry point for JWT.
                        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                        .and()
                        //Definition of the Session Policy.
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .and()
                        .authorizeRequests()
                        // Pages wich are authorized for everybody.
                        .antMatchers("/", "/home", "/register", "/addUser", "/login", "/h2-console/**", "/css/**", "/images/**", "/search", "/loginRedirect", "/addFav/{link}&{mail}", "/fav/{mail}", "/fav", "/delFav/{link}&{mail}", "/logout", "/clear", "/loginError", "/api/**").permitAll()

                        //.anyRequest().authenticated()
                        
                        //Logout page
                        .and()
                        .logout()
                        .logoutSuccessUrl("/")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                        //Login page
                        .and()  
                        .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/loginError").permitAll();

                //Definition of the Authentication provider.
                http.authenticationProvider(authenticationProvider());

                //Adding the JWT filter.
                http.addFilterBefore(authentificationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

                //Disabling the CSRF process for api.
                http.csrf().ignoringAntMatchers("/api/**");

                return http.build();
        }
        
}
