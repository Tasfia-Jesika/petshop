package com.example.petshop.controller;

import com.example.petshop.RequestTemplate.PostRequest;
import com.example.petshop.model.Comment;
import com.example.petshop.model.Post;
import com.example.petshop.repository.PostRepository;
import com.example.petshop.repository.UserRepository;
import com.example.petshop.service.JwtService;
import com.example.petshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

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
            if(userId == -1){
                hashMap.put("message", "Invalid User");
                hashMap.put("code", HttpStatus.BAD_REQUEST);
            }else{
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
            }
        }catch (Exception e){
            hashMap.put("message", "Internal Server Error");
            hashMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            return new ResponseEntity<HashMap>((HashMap) hashMap, (HttpStatus) hashMap.get("code"));
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<HashMap> commentByPostId(HttpServletRequest httpServletRequest){
        HashMap<String, Object> hashMap = new HashMap<>();
        String username= jwtService.extractUserName(jwtService.parseToken(httpServletRequest));
        Integer userId= userService.getUserIdByUserName(username);
        List<Post> postList = null;
        postList = postRepository.findByUserId(userId);
        hashMap.put("message", "Post List");
        hashMap.put("code", HttpStatus.OK);
        hashMap.put("data", postList);
        return new ResponseEntity<>(hashMap, (HttpStatus) hashMap.get("code"));
    }
}
