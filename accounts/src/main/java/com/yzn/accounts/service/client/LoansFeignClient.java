package com.yzn.accounts.service.client;

import com.yzn.accounts.dto.LoansDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value="/api/loans/details", consumes = "application/json")
    public ResponseEntity<LoansDTO> getLoanDetails(@RequestParam String mobileNumber);


}
