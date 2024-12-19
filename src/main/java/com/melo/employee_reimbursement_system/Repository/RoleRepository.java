package com.melo.employee_reimbursement_system.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.employee_reimbursement_system.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByRoleName(String roleName);
}
