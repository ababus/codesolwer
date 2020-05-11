// Copyright (c) 2019 Mastercard, VocaLink Ltd
package com.endava.internship.codesolver.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("datasource.flexypool")
@Validated
@Getter
@Setter
public class FlexyPoolProperties {

    @NotNull
    private String uniqueId;

    @NotNull
    private Integer maxOverflowPoolSize;

    @NotNull
    private Integer retryAttempts;
}
