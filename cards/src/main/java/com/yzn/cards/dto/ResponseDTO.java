package com.yzn.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Response Object"
)
public class ResponseDTO {

    private String statusCode;

    private String statusMessage;
}
