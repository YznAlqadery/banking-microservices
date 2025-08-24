package com.yzn.accounts.controller;

import com.yzn.accounts.dto.CustomerDTO;
import com.yzn.accounts.dto.ErrorResponseDTO;
import com.yzn.accounts.dto.ResponseDTO;
import com.yzn.accounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Accounts",
        description = "Create,Update,Get,Delete REST APIs for Accounts"
)
@Validated // -> Tell Spring to validate the input parameters of the method by default
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    private final AccountsService accountsService;

    @Operation(
            summary = "Create Account REST API",
            description = "To create a new Customer & Account in the Bank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount( @Valid @RequestBody CustomerDTO customerDTO) {

        accountsService.createAccount(customerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201", "Account created successfully"));
    }


    @Operation(
            summary = "Get Account Details REST API",
            description = "To get the details of the Customer & Account in the Bank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/details")
    public ResponseEntity<CustomerDTO> getAccountDetails(@RequestParam
                                                             @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
                                                         String mobileNumber) {

        CustomerDTO customerDTO = accountsService.getAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);

    }

    @Operation(
            summary = "Update Account REST API",
            description = "To update the details of the Customer & Account in the Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status 500 Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<CustomerDTO> updateAccount( @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = accountsService.updateAccount(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @Operation(
            summary = "Delete Account REST API",
            description = "To delete the details of the Customer & Account in the Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status 500 Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
                                                         String mobileNumber) {
        accountsService.deleteAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("200", "Account deleted successfully"));
    }

    @Operation(
            summary = "Get All Accounts REST API",
            description = "To get all the details of the Customers & Accounts in the Bank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("")
    public List<CustomerDTO> getAccounts() {
        return accountsService.getAllAccounts();
    }
}
