package com.melo.employee_reimbursement_system.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.employee_reimbursement_system.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByRoleName(String roleName);
    Boolean existsByRoleName(String roleName);
    void deleteByRoleName(String roleName);
    Role findById(long roleId);
}
