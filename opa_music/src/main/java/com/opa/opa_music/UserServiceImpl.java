package com.opa.opa_music;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

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

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User with name :" + name + " not found.");
        } else {
            opt.get();
        }
        return UserDetailsImpl.build(opt.get());
    }
}
