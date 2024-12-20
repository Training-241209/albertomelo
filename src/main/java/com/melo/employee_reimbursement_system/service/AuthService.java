package com.melo.employee_reimbursement_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.melo.employee_reimbursement_system.model.Role;
import com.melo.employee_reimbursement_system.model.Users;
import com.melo.employee_reimbursement_system.repository.RoleRepository;
import com.melo.employee_reimbursement_system.repository.UsersRepository;

@Service
public class AuthService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(Users user){
        if(usersRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username is already taken.");
        }

        if(usersRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email is already in use.");
        }

        String hashedpassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedpassword);

        Role employeeRole = roleRepository.findByRoleName("Employee");
        user.setRole(employeeRole);

        usersRepository.save(user);

    }

    public String authenticateUser(String username, String enteredPassword) {
        Users user = usersRepository.findByUsername(username);

        if(user == null || !passwordEncoder.matches(enteredPassword, user.getPassword())){
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtService.generateToken(user);
    }

}
