package com.yzn.accounts.controller;

import com.yzn.accounts.dto.AccountsDTO;
import com.yzn.accounts.dto.CustomerDTO;
import com.yzn.accounts.dto.ResponseDTO;
import com.yzn.accounts.service.AccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO) {

        accountsService.createAccount(customerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201", "Account created successfully"));
    }


    @GetMapping("/details")
    public ResponseEntity<CustomerDTO> getAccountDetails(@RequestParam("mobileNumber") String mobileNumber) {

        CustomerDTO customerDTO = accountsService.getAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);

    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDTO> updateAccount(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = accountsService.updateAccount(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam("mobileNumber") String mobileNumber) {
        accountsService.deleteAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("200", "Account deleted successfully"));
    }
}
