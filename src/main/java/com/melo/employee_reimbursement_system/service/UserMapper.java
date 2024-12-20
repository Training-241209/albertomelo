package com.melo.employee_reimbursement_system.service;

import org.springframework.stereotype.Component;

import com.melo.employee_reimbursement_system.dto.ReimbursementResponseDTO;
import com.melo.employee_reimbursement_system.dto.UsersDTO;
import com.melo.employee_reimbursement_system.model.Reimbursement;
import com.melo.employee_reimbursement_system.model.Users;

@Component
public class UserMapper {
    public UsersDTO convertToUsersDTO(Users user) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUserId(user.getUserId());
        usersDTO.setUsername(user.getUsername());
        usersDTO.setEmail(user.getEmail());
        usersDTO.setRoleName(user.getRole().getRoleName()); 
        usersDTO.setFullName(user.getFirstname() + " " + user.getLastname());
        return usersDTO;
    }

    public ReimbursementResponseDTO convertToReimbursementResponseDTO(Reimbursement reimbursement) {
        ReimbursementResponseDTO responseDTO = new ReimbursementResponseDTO();
        responseDTO.setReimbId(reimbursement.getReimbId());
        responseDTO.setDescription(reimbursement.getDescription());
        responseDTO.setAmount(reimbursement.getAmount());
        responseDTO.setStatus(reimbursement.getStatus());

        // Convert the associated user to UsersDTO and set it
        UsersDTO userDTO = convertToUsersDTO(reimbursement.getUser());
        responseDTO.setUser(userDTO);

        return responseDTO;
    }
}
