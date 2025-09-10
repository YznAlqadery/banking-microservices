package com.yzn.accounts.controller;


import com.yzn.accounts.dto.CustomerDetailsDTO;
import com.yzn.accounts.dto.ErrorResponseDTO;
import com.yzn.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "REST APIs for Customers in the Bank",
        description = "REST APIs to fetch Customer Details in the Bank"
)
@Validated
@RestController
@RequestMapping(path = "/api/customer", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch the details of the Customer in the Bank"
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
    })
    @GetMapping("/details")
    public ResponseEntity<CustomerDetailsDTO> getCustomerDetails(
            @RequestHeader("qaderi-correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
            String mobileNumber
    ){
        logger.debug("getCustomerDetails method started");
        CustomerDetailsDTO customerDetailsDTO = customerService.getCustomerDetails(mobileNumber, correlationId);
        logger.debug("getCustomerDetails method ended");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDTO);
    }

}
