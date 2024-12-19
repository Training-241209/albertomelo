package com.melo.employee_reimbursement_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reimbid")
    private long reimbId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private Users user;

    /* ---Constructors--- */
    public Reimbursement() { } //JPA Constructor

    public Reimbursement(String description, double amount, String status, Users user){
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.user = user;
    }

    /* ---Getters and Setters--- */

    public long getReimbId(){
        return reimbId;
    }

    public void setReimbId(long reimbId){
        this.reimbId = reimbId;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Users getUser(){
        return user;
    }

    public void setUser(Users user){
        this.user = user;
    }
}
