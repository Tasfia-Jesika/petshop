package com.example.petshop.controller;

import com.example.petshop.RequestTemplate.SignupRequest;
import com.example.petshop.action.SignupAction;
import com.example.petshop.model.User;
import com.example.petshop.model.UserDetailsModel;
import com.example.petshop.repository.UserDetailsRepository;
import com.example.petshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SignupAction signupAction;
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @RequestMapping("/user/{id}")
    public User user(@PathVariable String id){
        Integer userid=Integer.parseInt(id);
        Optional<User> user=userRepository.findById(userid);
        if(user.isPresent()) return user.get();
        return null;
    }
    @RequestMapping("/signup")
    public ResponseEntity<HashMap<String, Object>> signup(@RequestBody SignupRequest signupRequest){
        String validationMessage = signupAction.validation(signupRequest);
        HashMap<String, Object> hashMap = new HashMap<>();
        if(!validationMessage.equals("Success")) {
            hashMap.put("code", HttpStatus.BAD_REQUEST);
            hashMap.put("message", validationMessage);
        }else{
            hashMap = signupAction.saveUser(signupRequest);
        }
        return new ResponseEntity<HashMap<String, Object>>(hashMap, (HttpStatus) hashMap.get("code"));
    }

    @GetMapping("get/{userId}")
    public ResponseEntity<HashMap> getUserInformation(@PathVariable Integer userId){
        Optional<UserDetailsModel> userDetailsModel = userDetailsRepository.findByUserId(userId);
        HashMap<String, Object> hashMap = new HashMap<>();
        if(userDetailsModel.isPresent()){
            hashMap.put("data", userDetailsModel.get());
            hashMap.put("message", "user details information");
            hashMap.put("code", HttpStatus.OK);
        }
        else{
            hashMap = null;
        }
        return new ResponseEntity<HashMap>((HashMap) hashMap, (HttpStatus) hashMap.get("code"));
    }
}
