package io.akikr.demopostgredbapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app.logging.filter")
public record AppLoggingProperties(
        boolean enabled,
        boolean includeRequestBody,
        boolean includeResponseBody,
        int maxBodyLength) {
}
