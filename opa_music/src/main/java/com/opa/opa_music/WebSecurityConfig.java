package com.opa.opa_music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
// Class wich is implementing Spring Security Confgurer
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private UserDetailsService uds;

        @Autowired
        private BCryptPasswordEncoder encoder;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(uds).passwordEncoder(encoder);
        }

        @Override
        // Method wich is implementing Spring Security Permission system
        protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                                // All Permissions : all pages wich are not in here are going to be
                                // innaccessible without connexion
                                .antMatchers("/", "/home", "/register", "/addUser", "/login", "/h2-console**",
                                                "/css/**", "/js/**", "/images/**", "/search", "/loginForm")
                                .permitAll()

                                // Default page after a successfull connection
                                .anyRequest().authenticated()
                                .and()
                                .formLogin()
                                .defaultSuccessUrl("/", true)

                                // Default page after a successful logout
                                .and()
                                .logout()
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                                // Default page after a access denied (role not allowed to go here)
                                .and()
                                .exceptionHandling()
                                .accessDeniedPage("/accessDenied")

                                // link to the page of login
                                .and()
                                .formLogin()
                                .loginPage("/login");
        }
}
