package com.codegym.model.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class TransferDTO implements Validator {

    @NotEmpty(message = "ID người nhận không được để trống")
    @Pattern(regexp = "^\\d+$", message = "ID người nhận phải là số")
    private String recipientId;
    @NotEmpty(message = "TransferAmount is required")
    @Pattern(regexp = "^\\d+$", message = "Số tiền gửi phải là số")
    private String transferAmount;

    public TransferDTO() {
    }

    public TransferDTO(String recipientId, String transferAmount) {
        this.recipientId = recipientId;
        this.transferAmount = transferAmount;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
    @Override
    public void validate(Object o, Errors errors) {

    }
}