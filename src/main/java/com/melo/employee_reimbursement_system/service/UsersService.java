package com.melo.employee_reimbursement_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.melo.employee_reimbursement_system.dto.UsersDTO;
import com.melo.employee_reimbursement_system.model.Role;
import com.melo.employee_reimbursement_system.model.Users;
import com.melo.employee_reimbursement_system.repository.RoleRepository;
import com.melo.employee_reimbursement_system.repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;


    public List<UsersDTO> getAllUsers(String token){
        Users currentUser = jwtService.decodeToken(token);
        
        if(currentUser.getRole().getRoleName().equals("Manager") 
        || currentUser.getRole().getRoleName().equals("Admin")){
            List<Users> users = usersRepository.findAll();
            return users.stream()
                    .map(user -> userMapper.convertToUsersDTO(user))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Unauthorized: Only Managers can access all users.");
        }
    }

    public Users getUserById(long userId){
        if(!usersRepository.existsById(userId)){
            throw new IllegalArgumentException("User with ID: " + userId + "doesn't exist"); 
        }

        return usersRepository.findById(userId).get();
    }

    public Users promoteUserById(long userId, String token){
        Users currentUser = jwtService.decodeToken(token);
        
        if(currentUser.getRole().getRoleName().equals("Manager") 
        || currentUser.getRole().getRoleName().equals("Admin") )
        {
            if(!usersRepository.existsById(userId)){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"User with ID: " + userId + " doesn't exist"); 
            }

            Users userToPromote = usersRepository.findByUserId(userId);

            Role managerRole = roleRepository.findByRoleName("Manager");
            
            userToPromote.setRole(managerRole);

            return usersRepository.save(userToPromote);

        } else {
            throw new IllegalArgumentException("Unauthorized: Only Managers can promote a user.");
        }
    }

    public void deleteUserById(long userId, String token){
        Users currentUser = jwtService.decodeToken(token);
        
        if(currentUser.getRole().getRoleName().equals("Manager") 
        || currentUser.getRole().getRoleName().equals("Admin") )
        {
            if(!usersRepository.existsById(userId)){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"User with ID: " + userId + " doesn't exist"); 
            }
            usersRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("Unauthorized: Only Managers can delete a user.");
        }
    }

    public boolean existsByUsername(String username){
        return usersRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return usersRepository.existsByUsername(email);
    }

    public Users findByUsername(String username){
        return usersRepository.findByUsername(username);
    }

}
