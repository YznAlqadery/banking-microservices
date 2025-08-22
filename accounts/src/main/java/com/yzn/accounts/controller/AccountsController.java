package com.yzn.accounts.controller;

import com.yzn.accounts.dto.AccountsDTO;
import com.yzn.accounts.dto.CustomerDTO;
import com.yzn.accounts.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201", "Account created successfully"));
    }


}
