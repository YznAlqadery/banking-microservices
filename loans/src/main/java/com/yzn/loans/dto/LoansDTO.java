package com.yzn.loans.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Setter;

@Schema(
        name = "Loans",
        description = "Loans Object")
@Data
public class LoansDTO {

    @Schema(
            description = "Mobile Number",
            example = "1234567890"
    )
    @NotEmpty(message = "Mobile Number is required")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    private String mobileNumber;


    @Schema(
            description = "Loan Number",
            example = "123456789012"
    )
    @NotEmpty(message = "Loan Number is required")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    private String loanNumber;


    @NotEmpty(message = "Loan Type is required")
    @Schema(
            description = "Loan Type",
            example = "Personal Loan"
    )
    private String loanType;

    @Positive(message = "Total Loan must be greater than 0")
    @Schema(
            description = "Total Loan",
            example = "10000"
    )
    private Integer totalLoan;

    @Schema(
            description = "Amount Paid",
            example = "2500"
    )
    @PositiveOrZero(message = "Amount Paid must be greater than or equal to 0")
    private Integer amountPaid;

    @PositiveOrZero(message = "Outstanding Amount must be greater than or equal to 0")
    @Schema(
            description = "Outstanding Amount",
            example = "90322"
    )
    private Integer outstandingAmount;

}
