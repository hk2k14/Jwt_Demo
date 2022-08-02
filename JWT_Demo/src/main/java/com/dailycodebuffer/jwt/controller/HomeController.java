package com.dailycodebuffer.jwt.controller;

import com.dailycodebuffer.jwt.model.JwtRequest;
import com.dailycodebuffer.jwt.model.JwtResponse;
import com.dailycodebuffer.jwt.service.UserService;
import com.dailycodebuffer.jwt.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
public class HomeController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String home(){
        return "Welcome to Code Buffer";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(JwtRequest jwtRequest) throws Exception{

    try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
    }catch (BadCredentialsException e){
        //return new JwtResponse("Invalid Credentials", e);
        System.out.println("Invalid Credentials");
    }

    final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());

        System.out.println("Till here");

    final String token = jwtUtility.generateToken(userDetails);


    return new JwtResponse(token);


    }

}
