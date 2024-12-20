package com.melo.employee_reimbursement_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.employee_reimbursement_system.dto.UsersDTO;
import com.melo.employee_reimbursement_system.model.Users;
import com.melo.employee_reimbursement_system.service.UsersService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/managers/allusers")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {
        List<UsersDTO> users = usersService.getAllUsers(token);
        return ResponseEntity.ok().body(users);
    }

    //delete a user
    @DeleteMapping("/managers/deleteuser/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @PathVariable long userId) {
        usersService.deleteUserById(userId, token);
        return ResponseEntity.ok().body("User deleted.");
    }

    //update a user
    @PatchMapping("/managers/promoteuser/{userId}")
    public ResponseEntity<?> promoteUser(@RequestHeader("Authorization") String token, @PathVariable long userId){
        Users promotedUser = usersService.promoteUserById(userId, token);
        return ResponseEntity.ok().body(promotedUser);
    }
    
}
