package com.melo.employee_reimbursement_system.dto;


public class ReimbursementResponseDTO {

    private long reimbId;
    private String description;
    private double amount;
    private String status;
    private UsersDTO user;


    // Getters and setters
    public long getReimbId() {
        return reimbId;
    }

    public void setReimbId(long reimbId) {
        this.reimbId = reimbId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }
}
