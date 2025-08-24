package com.yzn.loans.controller;

import com.yzn.loans.dto.LoansDTO;
import com.yzn.loans.dto.ResponseDTO;
import com.yzn.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @GetMapping
    public ResponseEntity<List<LoansDTO>> getAllLoans() {
        return new ResponseEntity<>(loanService.getLoans(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get loan details",
            description = "Get loan details by mobile number"
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
    public ResponseEntity<ResponseDTO> createLoan(@RequestParam String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201","Loan created successfully"));
    }

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
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoan(@RequestParam String mobileNumber) {
        loanService.deleteLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("200","Loan deleted successfully"));
    }
}
