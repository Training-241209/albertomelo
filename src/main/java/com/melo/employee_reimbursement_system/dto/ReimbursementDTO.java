package com.melo.employee_reimbursement_system.dto;

public class ReimbursementDTO {
    private double amount;
    private String description;
    
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

