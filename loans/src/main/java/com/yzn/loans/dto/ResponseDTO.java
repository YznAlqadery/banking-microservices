package com.yzn.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Response Object",
        description = "Scheme to hold successful response information"
)
@Data
@AllArgsConstructor
public class ResponseDTO {

    @Schema(
            description = "Status code",
            example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Status message",
            example = "Loan created successfully"
    )
    private String statusMessage;
}
