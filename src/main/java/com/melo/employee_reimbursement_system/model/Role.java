package com.melo.employee_reimbursement_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private long roleId;

    @Column(name = "rolename", nullable = false, unique = true)
    private String roleName;

    /*---Constructors---*/
    public Role() { } //JPA constructor

    public Role(String roleName){
        this.roleName = roleName;
    }

    /*---Getters & Setters---*/
    public long getRoleId(){
        return roleId;
    }

    public void setRoleId(long roleId){
        this.roleId = roleId;
    }

    public String getRoleName(){
        return roleName;
    }

    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

}
