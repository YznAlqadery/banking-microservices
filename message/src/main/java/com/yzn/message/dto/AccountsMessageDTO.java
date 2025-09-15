package com.yzn.message.dto;

public record AccountsMessageDTO(
        Long accountNumber,
        String name,
        String email,
        String mobileNumber
) {
}
