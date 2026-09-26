package com.VaultDrop.VaultDropChatGptMiniProject.controller;


import com.VaultDrop.VaultDropChatGptMiniProject.model.Users;
import com.VaultDrop.VaultDropChatGptMiniProject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user){
        Users usr= service.register(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usr);
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user){
        String token= service.login(user);

        return ResponseEntity.ok(token);
    }





}
