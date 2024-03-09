package com.example.OrderService.config;

import com.example.OrderService.model.User;
import com.example.OrderService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user = this.userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user"));
        return new com.example.OrderService.config.UserDetails(user);
    }

}
