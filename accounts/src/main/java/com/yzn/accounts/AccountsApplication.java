package com.yzn.accounts;

import com.yzn.accounts.dto.AccountsContactInfoDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@EnableConfigurationProperties(AccountsContactInfoDTO.class)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts Microservice API",
                description = "Accounts Microservice REST API",
                version = "v1",
                contact = @Contact(
                        name = "Yazan Alqadery",
                        email = "yazanalqadery@gmail.com",
                        url = "https://github.com/yznalqadery"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://github.com/yznalqadery"
                )
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
