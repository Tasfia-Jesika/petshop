package com.example.petshop.controller;

import com.example.petshop.RequestTemplate.PostRequest;
import com.example.petshop.model.Post;
import com.example.petshop.repository.PostRepository;
import com.example.petshop.service.JwtService;
import com.example.petshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    PostRepository postRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<HashMap> createNewPost(@RequestBody PostRequest postRequest, HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        try{
            String username= jwtService.extractUserName(jwtService.parseToken(request));
            Integer userId= userService.getUserIdByUserName(username);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Timestamp date = new Timestamp(System.currentTimeMillis());
            Post post = new Post();
            post.setPost(postRequest.getPost());
            post.setUserId(userId);
            post.setCreatedAt(date);
            post.setUpdatedAt(date);
            postRepository.save(post);
            hashMap.put("message", "Posted Successfully");
            hashMap.put("code", HttpStatus.OK);
        }catch (Exception e){
            hashMap.put("message", "Internal Server Error");
            hashMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            return new ResponseEntity<HashMap>((HashMap) hashMap, (HttpStatus) hashMap.get("code"));
        }
    }
}
