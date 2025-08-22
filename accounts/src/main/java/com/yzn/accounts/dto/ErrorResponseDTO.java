package com.yzn.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private String apiPath;

    private HttpStatus errorCode;

    private String errorMessage;

    private String errorTime;

}
