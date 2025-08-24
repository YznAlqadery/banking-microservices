package com.yzn.cards.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Error Response Object"
)
public class ErrorResponseDTO {

    private String apiCode;

    private HttpStatus errorCode;

    private String errorMessage;

    private LocalDateTime errorTime;
}
