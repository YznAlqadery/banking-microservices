package com.yzn.accounts.service.client;

import com.yzn.accounts.dto.LoansDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {
    @Override
    public ResponseEntity<LoansDTO> getLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
