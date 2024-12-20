package com.melo.employee_reimbursement_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.employee_reimbursement_system.dto.ReimbursementDTO;
import com.melo.employee_reimbursement_system.dto.ReimbursementResponseDTO;
import com.melo.employee_reimbursement_system.dto.ReimbursementUpdateDTO;
import com.melo.employee_reimbursement_system.service.ReimbursementService;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {
    
    @Autowired
    private ReimbursementService reimbursementService;

    //create a reimbursement
    @PostMapping("/create")
    public ResponseEntity<?> createReimbursement(@RequestHeader("Authorization") String token, @RequestBody ReimbursementDTO reimbursementDTO) {
        ReimbursementResponseDTO createdReimbursement = reimbursementService.createReimbursement(reimbursementDTO, token);
        return ResponseEntity.ok().body(createdReimbursement); 
    }

    //get Reimbursement by ID
    @GetMapping("/{reimbdId}")
    public ResponseEntity<?> getReimbursementById(@RequestHeader("Authorization") String token, @PathVariable Long reimbdId) {
        ReimbursementResponseDTO reimbursement = reimbursementService.getReimbursementByReimbursementId(reimbdId, token);
        return ResponseEntity.ok().body(reimbursement);
    }

    //get all of my reimbursements
    @GetMapping("/me/{userId}")
    public ResponseEntity<?> getReimbursementByUserId(@RequestHeader("Authorization") String token, @PathVariable Long userId) {
        List<ReimbursementResponseDTO> reimbursements = reimbursementService.getAllReimbursementsByUserId(userId, token);
        return ResponseEntity.ok().body(reimbursements);
    }

    //get everybody's reimbursement(manager)
    @GetMapping("/manager/all")
    public ResponseEntity<?> getAllReimbursements(@RequestHeader("Authorization") String token){
        List<ReimbursementResponseDTO> reimbursements = reimbursementService.getAllReimbursements(token);
        return ResponseEntity.ok().body(reimbursements);
    }

    //update my reimbursement
    @PatchMapping("/me/update/{reimbId}")
    public ResponseEntity<?> updateReimbursementDescription(@RequestHeader("Authorization") String token, @PathVariable long reimbId, @RequestBody ReimbursementUpdateDTO reimbursementUpdateRequest) {
        String newDescription = reimbursementUpdateRequest.getNewReimbursementDescription();
        reimbursementService.updateReimbursementById(reimbId, newDescription, token);
        ReimbursementResponseDTO reimbursement = reimbursementService.getReimbursementByReimbursementId(reimbId, token);
        return ResponseEntity.status(HttpStatus.OK).body(reimbursement);
    }

    //approve reimbursement
    @PatchMapping("/manager/status/{reimbId}")
    public ResponseEntity<?> approveReimbursement(@RequestHeader("Authorization") String token, @PathVariable long reimbId, @RequestBody ReimbursementUpdateDTO reimbursementUpdateRequest) {
        String newStatus = reimbursementUpdateRequest.getNewStatus();
        reimbursementService.approveOrDenyReimbursement(reimbId, newStatus, token);
        ReimbursementResponseDTO reimbursement = reimbursementService.getReimbursementByReimbursementId(reimbId, token);
        return ResponseEntity.status(HttpStatus.OK).body(reimbursement);
    }

    //delete a reimbursement
    @DeleteMapping("/managers/deletereimbursement/{reimbId}")
    public ResponseEntity<?> deleteReimbursement(@RequestHeader("Authorization") String token, @PathVariable long reimbId) {
        reimbursementService.deleteReimbursementById(reimbId, token);
        return ResponseEntity.ok().body("Reimbursement deleted.");
    }


}
