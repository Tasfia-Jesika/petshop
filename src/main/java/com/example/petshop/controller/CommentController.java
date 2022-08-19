package com.example.petshop.controller;

import com.example.petshop.RequestTemplate.CommentRequest;
import com.example.petshop.RequestTemplate.PostRequest;
import com.example.petshop.model.Comment;
import com.example.petshop.model.Post;
import com.example.petshop.repository.CommentRepository;
import com.example.petshop.repository.PostRepository;
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
import java.util.Optional;

@RestController
public class CommentController {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    @RequestMapping(value = "/{postId}/comment/add", method = RequestMethod.POST)
    public ResponseEntity<HashMap> createNewPost(@PathVariable Integer postId, @RequestBody CommentRequest commentRequest, HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        try{
            String username= jwtService.extractUserName(jwtService.parseToken(request));
            Integer userId= userService.getUserIdByUserName(username);
            Optional<Post> post = postRepository.findById(postId);
            if(userId == -1) {
                hashMap.put("message", "Invalid User");
                hashMap.put("code", HttpStatus.BAD_REQUEST);
            }
            else if(!post.isPresent()){
                hashMap.put("message", "Invalid Post");
                hashMap.put("code", HttpStatus.BAD_REQUEST);
            }
            else{
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Timestamp date = new Timestamp(System.currentTimeMillis());
                Comment comment = new Comment(userId, postId, commentRequest.getComment(), date, date);
                commentRepository.save(comment);
                hashMap.put("message", "Commented Successfully");
                hashMap.put("code", HttpStatus.OK);
            }
        }catch (Exception e){
            hashMap.put("message", "Internal Server Error");
            hashMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            return new ResponseEntity<HashMap>((HashMap) hashMap, (HttpStatus) hashMap.get("code"));
        }
    }

    @RequestMapping(value = "/{postId}/comment/list", method = RequestMethod.GET)
    public ResponseEntity<HashMap> commentByPostId(@PathVariable Integer postId){
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Comment> commentList = null;
        commentList = commentRepository.findByPostId(postId);
        hashMap.put("message", "Comment List");
        hashMap.put("code", HttpStatus.OK);
        hashMap.put("data", commentList);
        return new ResponseEntity<>(hashMap, (HttpStatus) hashMap.get("code"));
    }
}
