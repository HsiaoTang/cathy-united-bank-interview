package com.cub.interview.cathyunitedbankinterview.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "exchange-rate.source")
@Data
public class ExchangeRateSourceProperties {

    private String url;
}
