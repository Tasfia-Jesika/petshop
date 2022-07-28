package com.example.petshop.service;

import com.example.petshop.RequestTemplate.SignupRequest;
import com.example.petshop.model.User;
import com.example.petshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;



    @Transactional
    public String saveUser(User user){
        try{
            userRepository.save(user);
        }catch (Exception e){
            return "DB Error during user registration!";
        }
        return "Registration Successful";
    }

    public boolean userNameExist(String username){
        Optional<User> user= userRepository.findByUsername(username);
        //if(!user.isPresent()) return false;
        return user.isPresent();
    }

    public Optional<User> fetchByCredential(String credential){
        Optional<User> user=userRepository.findByUsername(credential);
        if(!user.isPresent()) user= userRepository.findByEmailAddress(credential);
        return user;
    }

    public String encodePassword(String plainText){
        //return BCrypt.hashpw(plainText, BCrypt.gensalt());
        return passwordEncoder.encode(plainText);
    }
}
