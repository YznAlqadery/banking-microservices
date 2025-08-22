package com.yzn.accounts.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AccountsDTO {

    private Long accountNumber;

    private String accountType;

    private String branchAddress;


}
