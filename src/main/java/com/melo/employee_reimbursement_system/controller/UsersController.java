package com.melo.employee_reimbursement_system.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.employee_reimbursement_system.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;


}
