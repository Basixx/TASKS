package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CompanyConfig {
    @Value("${info.app.name}")
    private String companyName;

    @Value("${info.app.owner.name}")
    private String companyOwnerFirstName;

    @Value("${info.app.owner.surname}")
    private String companyOwnerLastName;

    @Value("${info.app.administrator.email}")
    private String companyEmail;
}
