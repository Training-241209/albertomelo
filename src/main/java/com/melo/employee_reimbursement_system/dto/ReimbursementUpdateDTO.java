package com.melo.employee_reimbursement_system.dto;


public class ReimbursementUpdateDTO {
    private String newReimbursementDescription;
    private String newStatus;

    // Getters and setters
    public String getNewReimbursementDescription() {
        return newReimbursementDescription;
    }

    public void setNewReimbursementDescription(String newReimbursementDescription) {
        this.newReimbursementDescription = newReimbursementDescription;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
