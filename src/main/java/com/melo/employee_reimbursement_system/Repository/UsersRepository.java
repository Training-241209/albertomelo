package com.melo.employee_reimbursement_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.employee_reimbursement_system.model.Users;


public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByUsername(String username);
    Users findByUsername(String username);
    boolean existsByEmail(String email);
    Users findByUserId(Long userId);
}
