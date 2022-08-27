package com.example.petshop.controller;

import com.example.petshop.RequestTemplate.CommentRequest;
import com.example.petshop.RequestTemplate.MessageRequest;
import com.example.petshop.model.Comment;
import com.example.petshop.model.Message;
import com.example.petshop.model.Post;
import com.example.petshop.repository.MessageRepository;
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
@RequestMapping("/message")
public class MessageController {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(value = "/send/{receiverId}", method = RequestMethod.POST)
    public ResponseEntity<HashMap> send(@PathVariable Integer receiverId, @RequestBody MessageRequest messageRequest, HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        try{
            String username= jwtService.extractUserName(jwtService.parseToken(request));
            Integer userId= userService.getUserIdByUserName(username);
            Timestamp date = new Timestamp(System.currentTimeMillis());
            Message message = new Message(userId, receiverId, messageRequest.getMessage(), date);
            messageRepository.save(message);
            hashMap.put("message", "Sent Message Successfully");
            hashMap.put("code", HttpStatus.OK);
        }catch (Exception e){
            hashMap.put("message", "Internal Server Error");
            hashMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            return new ResponseEntity<HashMap>((HashMap) hashMap, (HttpStatus) hashMap.get("code"));
        }
    }

    @RequestMapping(value = "/{receiverId}", method = RequestMethod.GET)
    public ResponseEntity<HashMap> send(@PathVariable Integer receiverId, HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        try{
            String username= jwtService.extractUserName(jwtService.parseToken(request));
            Integer userId= userService.getUserIdByUserName(username);
            Timestamp date = new Timestamp(System.currentTimeMillis());
            List<Message> messageList = messageRepository.findAllTheMessagesOfSpecificSenderAndReceiever(userId, receiverId);
            hashMap.put("data", messageList);
            hashMap.put("message", "Fetch Message Successfully");
            hashMap.put("code", HttpStatus.OK);
        }catch (Exception e){
            hashMap.put("message", "Internal Server Error");
            hashMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            return new ResponseEntity<HashMap>((HashMap) hashMap, (HttpStatus) hashMap.get("code"));
        }
    }
}
