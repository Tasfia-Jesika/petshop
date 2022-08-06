package com.example.petshop.service;

import com.example.petshop.RequestTemplate.SignupRequest;
import com.example.petshop.model.User;
import com.example.petshop.model.UserDetailsModel;
import com.example.petshop.repository.UserDetailsRepository;
import com.example.petshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    PasswordEncoder passwordEncoder;



    public HashMap<String, Object> saveUser(User user, UserDetailsModel userDetailsModel){
        HashMap<String, Object> hashMap = new HashMap<>();
        try{
            userRepository.save(user);
            Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
            userDetailsModel.setUserId(optionalUser.get().getId());
            userDetailsRepository.save(userDetailsModel);
        }catch (Exception e){
            hashMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
            hashMap.put("message", "DB Error during user registration!");
            return hashMap;
        }
        hashMap.put("code", HttpStatus.OK);
        hashMap.put("message", "Registration Successful");
        return hashMap;
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
    public Integer getUserIdByUserName(String username){
        Integer userId=-1;
        Optional<User> user= userRepository.findByUsername(username);
        if(user.isPresent()) userId=user.get().getId();
        /*try{
            userId=userRepository.findUserByUserName(username).getUserId();
        }
        catch (Exception e){
            System.out.println("Service: UserService, Method: getUserIdByUserName, Error:"+e.getMessage());
        }*/
        return userId;
    }
}
