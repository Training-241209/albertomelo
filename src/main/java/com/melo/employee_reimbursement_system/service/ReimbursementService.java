package com.melo.employee_reimbursement_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melo.employee_reimbursement_system.Repository.ReimbursementRepository;
import com.melo.employee_reimbursement_system.model.Reimbursement;

@Service
public class ReimbursementService {


    @Autowired
    private ReimbursementRepository reimbursementRepository;

    public Reimbursement createReimbursement(Reimbursement reimbursement){
        return reimbursementRepository.save(reimbursement);
    }

    public List<Reimbursement> getAllReimbursements(){
        return reimbursementRepository.findAll();
    }

    public List<Reimbursement> getAllReimbursementsByUserId(long userId){
        return reimbursementRepository.findByUser_UserId(userId);
    }

    public Reimbursement getReimbursementByReimbursementId(long reimbId){
        return reimbursementRepository.findById(reimbId)
            .orElseThrow(() -> new RuntimeException("Reimbursement not found with id: " + reimbId));
    }

    public Reimbursement deleteReimbursementById(long reimbId){
        Reimbursement reimbursement = getReimbursementByReimbursementId(reimbId);
        reimbursementRepository.deleteById(reimbId);
        return reimbursement;
    }
}
