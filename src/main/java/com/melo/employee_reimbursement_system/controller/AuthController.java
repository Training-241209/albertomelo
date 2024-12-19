package com.melo.employee_reimbursement_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.employee_reimbursement_system.dto.LoginRequest;
import com.melo.employee_reimbursement_system.model.Users;
import com.melo.employee_reimbursement_system.service.UsersService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = usersService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        if(!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.ok("Login Successful");
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Users user){
        if (usersService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        Users savedUser = usersService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
