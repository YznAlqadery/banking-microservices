package com.yzn.loans.controller;

import com.yzn.loans.dto.ErrorResponseDTO;
import com.yzn.loans.dto.LoansContactInfoDTO;
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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@Tag(
        name = "CRUD REST APIs for Loans in the bank",
        description = "CRUD REST APIs to CREATE, READ, UPDATE, DELETE loans"
)
public class LoansController {

    private static final Logger logger = LoggerFactory.getLogger(LoansController.class);

    private final LoanService loanService;

//    @Value("${build.version}")
//    private String buildVersion;

    //private final Environment environment;

    private final LoansContactInfoDTO loansContactInfoDTO;

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
    public ResponseEntity<LoansDTO> getLoanDetails(
            @RequestHeader("qaderi-correlation-id") String correlationId,
            @RequestParam String mobileNumber) {
        logger.debug("qaderi-correlation-id found: {} ", correlationId);
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



    @Operation(
            summary = "Get Contact Info REST API",
            description = "To get contact info of the loans microservice"
    )
    @ApiResponses(
            {
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
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDTO> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDTO);
    }
//
//    @Operation(
//            summary = "Get Java Version REST API",
//            description = "To get java version of the loans microservice"
//    )
//    @ApiResponses(
//            {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "HTTP Status 200 OK"
//                    ),
//                    @ApiResponse(
//                            responseCode = "500",
//                            description = "HTTP Status 500 Internal Server Error",
//                            content = @Content(
//                                    schema = @Schema(implementation = ErrorResponseDTO.class)
//                            )
//                    )
//            }
//    )
//    @GetMapping("/java-version")
//    public ResponseEntity<String> getEnvironment() {
//        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
//    }
//
//    @Operation(
//            summary = "Get Build Version REST API",
//            description = "To get build version of the loans microservice"
//    )
//    @ApiResponses(
//            {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "HTTP Status 200 OK"
//                    ),
//                    @ApiResponse(
//                            responseCode = "500",
//                            description = "HTTP Status 500 Internal Server Error",
//                            content = @Content(
//                                    schema = @Schema(implementation = ErrorResponseDTO.class)
//                            )
//                    )
//            }
//    )
//    @GetMapping("/version")
//    public ResponseEntity<String> getBuildVersion() {
//        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
//    }
}
