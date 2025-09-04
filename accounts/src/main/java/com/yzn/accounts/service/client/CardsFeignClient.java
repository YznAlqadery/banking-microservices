package com.yzn.accounts.service.client;

import com.yzn.accounts.dto.CardsDTO;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value="/api/details", consumes = "application/json")
    public ResponseEntity<CardsDTO> getCardDetails(
            @RequestHeader("qaderi-correlation-id") String correlationId,
            @RequestParam String mobileNumber);

}
