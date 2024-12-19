package com.melo.employee_reimbursement_system.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.employee_reimbursement_system.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByUsername(String username);
    Optional<Users> findByUsername(String username);
}
