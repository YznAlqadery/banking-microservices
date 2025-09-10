package com.yzn.cards.controller;

import com.yzn.cards.dto.CardsContactInfoDTO;
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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST APIs for Cards in bank",
        description = "CRUD REST APIs for Cards in bank"
)
@Validated
public class CardsController {

    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    private final CardsService cardsService;

//    @Value("${build.version}")
//    private String buildVersion;
//
//    private final Environment environment;
//
  private final CardsContactInfoDTO cardsContactInfoDTO;

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
    public ResponseEntity<CardsDTO> getCardDetails(
            @RequestHeader("qaderi-correlation-id") String correlationId,
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
            @RequestParam String mobileNumber) {
        logger.debug("getCardDetails method started");
        CardsDTO cardsDTO = cardsService.getCardDetails(mobileNumber);
        logger.debug("getCardDetails method ended");
        return ResponseEntity.status(HttpStatus.OK).body(cardsDTO);
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
    public ResponseEntity<ResponseDTO> updateCardDetails(@Valid @RequestBody CardsDTO cardsDto) {
        boolean isUpdated = cardsService.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Card updated successfully"));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO("417", "Card not updated"));
        }
    }

    @Operation(
            summary = "Get Contact Info REST API",
            description = "To get contact info of the cards microservice"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status 200 OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status 500 Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDTO> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(cardsContactInfoDTO);
    }
//
//    @Operation(
//            summary = "Get Java Version REST API",
//            description = "To get java version of the cards microservice"
//    )
//    @ApiResponses(
//            {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "HTTP Status 200 OK"
//                    ),
//                    @ApiResponse(
//                            responseCode = "500",
//                            description = "HTTP Status 500 Internal Server Error",
//                            content = @Content(
//                                    schema = @Schema(implementation = ErrorResponseDTO.class)
//                            )
//                    )
//            }
//    )
//    @GetMapping("/java-version")
//    public ResponseEntity<String> getEnvironment() {
//        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
//    }
//
//    @Operation(
//            summary = "Get Build Version REST API",
//            description = "To get build version of the cards microservice"
//    )
//    @ApiResponses(
//            {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "HTTP Status 200 OK"
//                    ),
//                    @ApiResponse(
//                            responseCode = "500",
//                            description = "HTTP Status 500 Internal Server Error",
//                            content = @Content(
//                                    schema = @Schema(implementation = ErrorResponseDTO.class)
//                            )
//                    )
//            }
//    )
//    @GetMapping("/version")
//    public ResponseEntity<String> getBuildVersion() {
//        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
//    }
}

