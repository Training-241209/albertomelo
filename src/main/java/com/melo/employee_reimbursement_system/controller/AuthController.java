package com.melo.employee_reimbursement_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.employee_reimbursement_system.dto.LoginRequestDTO;
import com.melo.employee_reimbursement_system.model.Users;
import com.melo.employee_reimbursement_system.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok().body(token); 
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        authService.registerUser(user); 
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered!");
    }
}
