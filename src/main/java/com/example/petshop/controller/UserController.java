package com.example.petshop.controller;

import com.example.petshop.RequestTemplate.SignupRequest;
import com.example.petshop.action.SignupAction;
import com.example.petshop.model.User;
import com.example.petshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SignupAction signupAction;

    @RequestMapping("/user/{id}")
    public User user(@PathVariable String id){
        Integer userid=Integer.parseInt(id);
        Optional<User> user=userRepository.findById(userid);
        if(user.isPresent()) return user.get();
        return null;
    }
    @RequestMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest){
        String validationMessage = signupAction.validation(signupRequest);
        if(!validationMessage.equals("Success")) {
            return validationMessage;
        }
        return signupAction.saveUser(signupRequest);
    }
}
