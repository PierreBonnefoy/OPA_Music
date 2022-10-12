package com.opa.opa_music;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Method witch is allowing to save a user into the database
    @Override
    public Integer saveUser(User user) {
        String pwd = user.getPassword();
        String encodedPwd = passwordEncoder.encode(pwd);
        user.setPassword(encodedPwd);
        user = userRepo.save(user);
        return user.getId();
    }

    // Method wich is returning true if username alreday took and false ever
    @Override
    public boolean isUsernameAlreadyTake(String name) {
        if (userRepo.findUserByName(name).isEmpty()) {
            return false;
        }
        return true;
    }

    // Method wich is creating a spring usere and verify if the user is exitent in
    // the database by his email
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> opt = userRepo.findUserByName(name);
        org.springframework.security.core.userdetails.User springUser = null;

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User with name :" + name + " not found.");
        } else {
            User user = opt.get();
            List<String> roles = user.getRoles();
            Set<GrantedAuthority> ga = new HashSet<>();
            for (String role : roles) {
                ga.add(new SimpleGrantedAuthority(role));
            }

            springUser = new org.springframework.security.core.userdetails.User(
                    name,
                    user.getPassword(),
                    ga);
        }
        return springUser;
    }
}
