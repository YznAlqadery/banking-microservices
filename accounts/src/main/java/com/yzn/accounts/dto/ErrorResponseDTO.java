package com.yzn.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response details"
)
@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    @Schema(
            description = "API path of the request"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the status of the request"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the status of the request"
    )
    private String errorMessage;

    @Schema(
            description = "Time when the error occurred"
    )
    private LocalDateTime errorTime;

}
