package com.example.petshop.action;

import com.example.petshop.RequestTemplate.SignupRequest;
import com.example.petshop.model.User;
import com.example.petshop.model.UserDetailsModel;
import com.example.petshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SignupAction {
    @Autowired
    UserService userService;

    public String validation(SignupRequest signupRequest){
        if(signupRequest.getUserName().length()==0) return "username can not be empty!";
        if(signupRequest.getFirstName().length()==0 || signupRequest.getLastName().length()==0) return "Name can not be empty!";
        if(signupRequest.getPassword().length()<8) return "Password should contain at least 8 characters!";
        if(userService.userNameExist(signupRequest.getUserName())) return "Username already exists!";
        return "Success";
    }

    public HashMap<String, Object> saveUser(SignupRequest signupRequest){
        User user=new User(signupRequest.getUserName(), signupRequest.getEmailAddress(), userService.encodePassword(signupRequest.getPassword()),"ROLE_user", true);
        UserDetailsModel userDetailsModel = new UserDetailsModel(signupRequest.getFirstName(), signupRequest.getMiddleName(), signupRequest.getLastName(), signupRequest.getPhoneNumber(), signupRequest.getAddress());
        return userService.saveUser(user, userDetailsModel);
    }
}
