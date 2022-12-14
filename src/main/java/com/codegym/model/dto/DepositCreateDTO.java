package com.codegym.model.dto;



import javax.validation.constraints.Pattern;

public class DepositCreateDTO {

    private Long id;

    @Pattern(regexp = "^\\d+$", message = "Sô tiền gửi phải là số")
    private String transactionAmount;

    @Pattern(regexp = "^\\d+$", message = "ID khách hàng không hợp lệ")
    private String customerId;

    public DepositCreateDTO() {
    }

    public DepositCreateDTO(Long id, String transactionAmount, String customerId) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}