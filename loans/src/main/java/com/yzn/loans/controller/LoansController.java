package com.yzn.loans.controller;

import com.yzn.loans.dto.ErrorResponseDTO;
import com.yzn.loans.dto.LoansDTO;
import com.yzn.loans.dto.ResponseDTO;
import com.yzn.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD REST APIs for Loans in the bank",
        description = "CRUD REST APIs to CREATE, READ, UPDATE, DELETE loans"
)
public class LoansController {

    private final LoanService loanService;

    @Operation(
            summary = "Get all loans",
            description = "Get all loans in the bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping
    public ResponseEntity<List<LoansDTO>> getAllLoans() {
        return new ResponseEntity<>(loanService.getLoans(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get loan details",
            description = "Get loan details by mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/details")
    public ResponseEntity<LoansDTO> getLoanDetails(@RequestParam String mobileNumber) {
        return ResponseEntity.ok(loanService.getLoanDetails(mobileNumber));
    }

    @Operation(
            summary = "Create loan",
            description = "Create a new loan"
    )

    @PostMapping("/create")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    public ResponseEntity<ResponseDTO> createLoan(@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits") @RequestParam String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201","Loan created successfully"));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan updated successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not updated")
    })
    @Operation(
            summary = "Update loan",
            description = "Update an existing loan"
    )

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateLoan(@RequestBody LoansDTO loansDTO) {
        loanService.updateLoan(loansDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("200","Loan updated successfully"));
    }

    @Operation(
            summary = "Delete loan",
            description = "Delete an existing loan"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoan(@RequestParam String mobileNumber) {
        loanService.deleteLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("200","Loan deleted successfully"));
    }
}
