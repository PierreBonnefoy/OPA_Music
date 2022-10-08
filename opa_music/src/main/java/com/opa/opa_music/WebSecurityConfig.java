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
        protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                                .antMatchers("/", "/home", "/register", "/addUser", "/h2-console").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .formLogin()
                                .defaultSuccessUrl("/hello", true)

                                .and()
                                .logout()
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                                .and()
                                .exceptionHandling()
                                .accessDeniedPage("/accessDenied");
        }
}
