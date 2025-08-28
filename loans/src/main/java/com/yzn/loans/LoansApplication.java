package com.yzn.loans;

import com.yzn.loans.dto.LoansContactInfoDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(LoansContactInfoDTO.class)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Loans Management API",
                version = "1.0",
                description = "Loans Management API",
                contact = @Contact(
                        name = "Yazan Alqadery",
                        email = "yazanalqadery@gmail.com",
                        url = "https://github.com/yznalqadery"
                )
        )
)
public class LoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
    }

}
