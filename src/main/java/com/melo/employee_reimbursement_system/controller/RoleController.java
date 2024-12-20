package com.melo.employee_reimbursement_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.employee_reimbursement_system.dto.RoleUpdateRequestDTO;
import com.melo.employee_reimbursement_system.model.Role;
import com.melo.employee_reimbursement_system.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //create a role(admin)
    @PostMapping("/createrole")
    public ResponseEntity<?> createRole(@RequestHeader("Authorization") String token, @RequestBody Role role) {
        roleService.createRole(role, token); 
        return ResponseEntity.status(HttpStatus.CREATED).body("Role successfully created!");
    }

    //update a role(admin)
    @PatchMapping("/updaterole/{roleId}")
    public ResponseEntity<?> updateRole(@RequestHeader("Authorization") String token, @PathVariable long roleId, @RequestBody RoleUpdateRequestDTO roleUpdateRequest) {
        String newRoleName = roleUpdateRequest.getNewRoleName();
        roleService.updateRoleById(roleId, newRoleName, token);
        return ResponseEntity.status(HttpStatus.OK).body("Role updated successfuly.");
    }

    //delete a role(admin)
    @DeleteMapping("/deleterole/{roleId}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @PathVariable long roleId) {
        roleService.deleteRoleById(roleId, token);
        return ResponseEntity.ok().body("User deleted.");
    }
    
}
