package com.yzn.cards;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "")
@OpenAPIDefinition(
        info = @Info(
                title = "Cards API",
                version = "1.0",
                description = "Cards API",
                contact = @Contact(
                        name = "Yazan Alqadery",
                        email = "yazanalqadery@gmail.com",
                        url = "https://github.com/yznalqadery"
        )
        )
)
public class CardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardsApplication.class, args);
    }

}
