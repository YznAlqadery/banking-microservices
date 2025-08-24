package com.yzn.loans.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class LoansDTO {

    @NotEmpty(message = "Mobile Number is required")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    private String mobileNumber;


    @NotEmpty(message = "Loan Number is required")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    private String loanNumber;

    @NotEmpty(message = "Loan Type is required")
    private String loanType;

    @Positive(message = "Total Loan must be greater than 0")
    private Integer totalLoan;

    @PositiveOrZero(message = "Amount Paid must be greater than or equal to 0")
    private Integer amountPaid;

    @PositiveOrZero(message = "Outstanding Amount must be greater than or equal to 0")
    private Integer outstandingAmount;

}
