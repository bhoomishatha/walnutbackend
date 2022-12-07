package com.walnutclinics.billingService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Walnut CRM API", version = "1.0", description = "Walnut CRM Information"))
public class BillingServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

}
