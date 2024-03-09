package com.example.OrderService.service;

import com.example.OrderService.exceptions.UserAlreadyExistsException;
import com.example.OrderService.model.Country;
import com.example.OrderService.model.User;
import com.example.OrderService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String username, String password, String location) throws UserAlreadyExistsException {

        if(userRepository.findByUsername(username).isPresent())
            throw new UserAlreadyExistsException("User already presented");
        User usersModel = new User(username,passwordEncoder.encode(password),location);
        userRepository.save(usersModel);
        return usersModel;
    }

}

