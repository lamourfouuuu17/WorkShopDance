package com.example.userservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    private String signingKey;
}
