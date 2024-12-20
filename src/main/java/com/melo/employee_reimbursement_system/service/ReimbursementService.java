package com.melo.employee_reimbursement_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.melo.employee_reimbursement_system.dto.ReimbursementDTO;
import com.melo.employee_reimbursement_system.dto.ReimbursementResponseDTO;
import com.melo.employee_reimbursement_system.model.Reimbursement;
import com.melo.employee_reimbursement_system.model.Users;
import com.melo.employee_reimbursement_system.repository.ReimbursementRepository;

@Service
public class ReimbursementService {

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    public ReimbursementResponseDTO createReimbursement(ReimbursementDTO reimbursementDTO, String token) {
        Users currentUser = jwtService.decodeToken(token);

        Reimbursement reimbursement = new Reimbursement();

        reimbursement.setDescription(reimbursementDTO.getDescription());
        reimbursement.setAmount(reimbursementDTO.getAmount());
        reimbursement.setStatus("PENDING");
        reimbursement.setUser(currentUser);

        Reimbursement savedReimbursement = reimbursementRepository.save(reimbursement);

        ReimbursementResponseDTO responseDTO = userMapper.convertToReimbursementResponseDTO(savedReimbursement);

        return responseDTO;
    }

    public List<ReimbursementResponseDTO> getAllReimbursements(String token) {
        Users currentUser = jwtService.decodeToken(token);

        if (currentUser.getRole().getRoleName().equals("Manager")
                || currentUser.getRole().getRoleName().equals("Admin")) {
            List<Reimbursement> reimbursements = reimbursementRepository.findAll();
            return reimbursements.stream()
                    .map(reimbursement -> userMapper.convertToReimbursementResponseDTO(reimbursement))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Only Managers can view all tickets.");
        }
    }

    public List<ReimbursementResponseDTO> getAllReimbursementsByUserId(long userId, String token) {
        Users currentUser = jwtService.decodeToken(token);

        if (currentUser.getUserId() == userId) {
            List<Reimbursement> reimbursements = reimbursementRepository.findByUser_UserId(userId);
            return reimbursements.stream()
                    .map(reimbursement -> userMapper.convertToReimbursementResponseDTO(reimbursement))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("You can only view your tickets.");
        }
    }

    public ReimbursementResponseDTO getReimbursementByReimbursementId(long reimbId, String token) {
        Users currentUser = jwtService.decodeToken(token);

        if (currentUser == null) {
            throw new IllegalArgumentException("You can only view this if you are the owner of the reimbursement.");
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,
                        "Reimbursement not found with id: " + reimbId));

        ReimbursementResponseDTO reimbursementResponse = userMapper.convertToReimbursementResponseDTO(reimbursement);

        return reimbursementResponse;
    }

    public ReimbursementResponseDTO updateReimbursementById(long reimbId, String newDescription, String token) {
        Users currentUser = jwtService.decodeToken(token);

        if (currentUser == null) {
            throw new IllegalArgumentException("You must be logged in to update a reimbursement.");
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,
                        "Reimbursement not found with id: " + reimbId));

        reimbursement.setDescription(newDescription);

        reimbursementRepository.save(reimbursement);

        return userMapper.convertToReimbursementResponseDTO(reimbursement);
    }

    public ReimbursementResponseDTO approveOrDenyReimbursement(long reimbId, String newStatus, String token) {
        Users currentUser = jwtService.decodeToken(token);

        if (currentUser.getRole().getRoleName().equals("Manager")
                || currentUser.getRole().getRoleName().equals("Admin")) {

            Reimbursement reimbursement = reimbursementRepository.findById(reimbId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,
                            "Reimbursement not found with id: " + reimbId));

            if (!"APPROVED".equalsIgnoreCase(newStatus) && !"DENIED".equalsIgnoreCase(newStatus)) {
                throw new IllegalArgumentException("Invalid status value. Only 'APPROVED' or 'DENIED' are allowed.");
            }

            reimbursement.setStatus(newStatus);

            reimbursementRepository.save(reimbursement);

            return userMapper.convertToReimbursementResponseDTO(reimbursement);
        } else {
            throw new IllegalArgumentException("Only Managers or Admins can approve or deny reimbursements.");
        }
    }

    public void deleteReimbursementById(long reimbId, String token) {
        Users currentUser = jwtService.decodeToken(token);

        if (currentUser.getRole().getRoleName().equals("Manager")
                || currentUser.getRole().getRoleName().equals("Admin")) {

            reimbursementRepository.deleteById(reimbId);
        } else {
            throw new IllegalArgumentException("Only Managers or Admins can delete reimbursements.");
        }
    }
}
