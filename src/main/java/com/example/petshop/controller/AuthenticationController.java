package com.example.petshop.controller;

import com.example.petshop.RequestTemplate.AuthenticationRequest;
import com.example.petshop.ResponseTemplate.AuthenticationResponse;
import com.example.petshop.model.User;
import com.example.petshop.service.JwtService;
import com.example.petshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    //@CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        Optional<User> user= userService.fetchByCredential(authenticationRequest.getCredential());
        if(!user.isPresent()) return ResponseEntity.ok("Incorrect credential or password!");
        try {
            UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(user.get().getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(
                    token);
        }
        catch (Exception e) {
            //throw new Exception("Incorrect credential or password!", e);
            return ResponseEntity.unprocessableEntity().body("Incorrect credential or password!");
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.get().getUsername());

        final String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/get-username")
    public String getUsername(HttpServletRequest request){
        return "token: "+jwtService.parseToken(request)+"\nusername: "+jwtService.extractUserName(jwtService.parseToken(request));
    }
}