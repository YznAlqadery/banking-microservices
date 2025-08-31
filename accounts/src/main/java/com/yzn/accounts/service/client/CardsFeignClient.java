package com.yzn.accounts.service.client;

import com.yzn.accounts.dto.CardsDTO;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value="/api/cards/details", consumes = "application/json")
    public ResponseEntity<CardsDTO> getCardDetails(@RequestParam String mobileNumber);

}
