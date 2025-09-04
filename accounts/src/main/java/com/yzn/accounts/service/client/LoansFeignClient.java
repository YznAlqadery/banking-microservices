package com.yzn.accounts.service.client;

import com.yzn.accounts.dto.LoansDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping(value="/api/details", consumes = "application/json")
    public ResponseEntity<LoansDTO> getLoanDetails(
            @RequestHeader("qaderi-correlation-id") String correlationId,
            @RequestParam String mobileNumber);


}
