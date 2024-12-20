package com.melo.employee_reimbursement_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.melo.employee_reimbursement_system.model.Role;
import com.melo.employee_reimbursement_system.model.Users;
import com.melo.employee_reimbursement_system.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;

     public void createRole(Role role, String token){
        Users currentUser = jwtService.decodeToken(token);

        if(currentUser.getRole().getRoleName().equals("Admin")){
            if(roleRepository.existsByRoleName(role.getRoleName())){
                throw new IllegalArgumentException("Role Name already exists");
            }

            roleRepository.save(role);
        } else {
            throw new IllegalArgumentException("Unauthorized: Only Admins can create a role.");
        }
    }

    public Role getRoleByName(String roleName) {
        if(!roleRepository.existsByRoleName(roleName)){
            throw new IllegalArgumentException("Role with " + roleName + "doesn't exist"); 
        }

        return roleRepository.findByRoleName(roleName);
    }

    public Role updateRoleById(long roleId, String newRoleName, String token) {
        Users currentUser = jwtService.decodeToken(token);

        if(currentUser.getRole().getRoleName().equals("Admin")){
            Role role = roleRepository.findById(roleId);

            if(role == null){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Role ID: " + roleId + " doesn't exist"); 
            }

            //if the role you're trying to update already has the name newRoleName or if newRoleName already exists in the db
            if(roleRepository.existsByRoleName(newRoleName) && !role.getRoleName().equals(newRoleName)){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Role with name " + newRoleName + " already exists");
            }

            role.setRoleName(newRoleName);

            return roleRepository.save(role);
        } else {
            throw new IllegalArgumentException("Unauthorized: Only Admins can update a role.");
        }
    }

    public void deleteRoleById(long roleId, String token) {
        Users currentUser = jwtService.decodeToken(token);

        if(currentUser.getRole().getRoleName().equals("Admin")){
            if(!roleRepository.existsById(roleId)){
                throw new IllegalArgumentException("Role ID: " + roleId + " doesn't exist"); 
            }

            roleRepository.deleteById(roleId);
        } else {
            throw new IllegalArgumentException("Unauthorized: Only Admins can delete a role.");
        }
    }

    public boolean roleExists(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
