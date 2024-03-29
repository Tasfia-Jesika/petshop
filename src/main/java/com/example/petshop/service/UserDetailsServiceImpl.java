package com.example.petshop.service;

import com.example.petshop.model.User;
import com.example.petshop.model.UserDetailsImpl;
import com.example.petshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user= userRepository.findByUsername(username);
        user.orElseThrow(()-> new UsernameNotFoundException("Not Found: "+username));
        return user.map(UserDetailsImpl::new).get();
    }
}