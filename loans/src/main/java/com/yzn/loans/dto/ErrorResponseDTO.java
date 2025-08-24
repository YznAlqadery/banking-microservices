package com.yzn.loans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private String apiPath;

    private String statusCode;

    private String statusMessage;

    private LocalDateTime errorTime;
}
