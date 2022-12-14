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

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
// Class wich is implementing Spring Security Confgurer
public class WebSecurityConfig {//extends WebSecurityConfigurerAdapter {
        @Autowired
        private UserDetailsService uds;

        @Autowired
        private BCryptPasswordEncoder encoder;

        @Autowired 
        private AuthEntryPointJwt unauthorizedHandler;

        @Bean
        public AuthTokenFilter authentificationJwtTokenFilter(){
                return new AuthTokenFilter();
        }

        @Bean
        protected AuthenticationManager authenficationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider(){
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

                authProvider.setUserDetailsService(uds);
                authProvider.setPasswordEncoder(encoder);

                return authProvider;
        }

        @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
                http.cors().disable()
                        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                        .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .and()
                        .authorizeRequests()
                        .antMatchers("/", "/home", "/register", "/addUser", "/login", "/h2-console/**", "/css/**", "/images/**", "/search", "/loginRedirect", "/addFav/{link}&{mail}", "/fav/{mail}", "/fav", "/delFav/{link}&{mail}", "/logout", "/clear", "/loginError", "/api/**").permitAll()

                        //.anyRequest().authenticated()
                        
                        .and()
                        .logout()
                        .logoutSuccessUrl("/")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                        .and()
                        .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/loginError").permitAll();

                http.authenticationProvider(authenticationProvider());

                http.addFilterBefore(authentificationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

                http.csrf().ignoringAntMatchers("/api/**");

                return http.build();
        }
        
}
