package com.yzn.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Cards and Loans information "
)
public class CustomerDetailsDTO {

    @Schema(
            description = "Name of the customer",
            example = "John Doe"
    )
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 25, message = "Name should have at least 5 characters")
    private String name;

    @Schema(
            description = "Email of the customer",
            example = "7oV9Q@example.com"
    )
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "1234567890"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the customer"
    )
    private AccountsDTO accountsDTO;

    @Schema(
            description = "Loans details of the customer"
    )
    private LoansDTO loansDTO;

    @Schema(
            description = "Cards details of the customer"
    )
    private CardsDTO cardsDTO;
}
