package com.yzn.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response Object",
        description = "Scheme to hold error response information"
)
@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    @Schema(
            description = "API Path",
            example = "/loans/create"
    )
    private String apiPath;

    @Schema(
            description = "Status Code",
            example = "200"
    )
    private HttpStatus statusCode;

    @Schema(
            description = "Status Message",
            example = "Loan created successfully"
    )
    private String statusMessage;

    @Schema(
            description = "Error Time",
            example = "2022-01-01T00:00:00"
    )
    private LocalDateTime errorTime;
}
