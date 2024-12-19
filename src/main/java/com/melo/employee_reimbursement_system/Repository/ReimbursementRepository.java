package com.melo.employee_reimbursement_system.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.employee_reimbursement_system.model.Reimbursement;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long>{
    List<Reimbursement> findByUser_UserId(Long userId);
}
