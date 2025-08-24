package com.yzn.cards.controller;

import com.yzn.cards.dto.CardsDTO;
import com.yzn.cards.dto.ErrorResponseDTO;
import com.yzn.cards.dto.ResponseDTO;
import com.yzn.cards.service.CardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Tag(
        name = "CRUD REST APIs for Cards in bank",
        description = "CRUD REST APIs for Cards in bank"
)
@Validated
public class CardsController {

    private final CardsService cardsService;

    @Operation(
            summary = "Get all cards",
            description = "Get all cards in bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("")
    public ResponseEntity<List<CardsDTO>> getAllCards() {
        return ResponseEntity.status(HttpStatus.OK).body(cardsService.getAllCards());
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @Operation(
            summary = "Get card details",
            description = "Get card details by mobile number"
    )
    @GetMapping("/details")
    public ResponseEntity<CardsDTO> getCardDetails( @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") @RequestParam String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(cardsService.getCardDetails(mobileNumber));
    }

    @Operation(
            summary = "Create new card",
            description = "Create new card by mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )

    @PostMapping("/create")
    public ResponseEntity<CardsDTO> createCard( @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") @RequestParam String mobileNumber) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardsService.createCard(mobileNumber));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })

    @Operation(
            summary = "Delete card",
            description = "Delete card by mobile number"
    )
    @DeleteMapping("/delete")
    public void deleteCard( @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") @RequestParam String mobileNumber) {
        cardsService.deleteCard(mobileNumber);
    }

    @Operation(
            summary = "Update card",
            description = "Update card by mobile number"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateCard(@Valid @RequestBody CardsDTO cardsDto) {

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("200", "Card updated successfully"));

    }
}
