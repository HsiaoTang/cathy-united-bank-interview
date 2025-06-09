package com.cub.interview.cathyunitedbankinterview.config;

import com.cub.interview.cathyunitedbankinterview.config.properties.ExternalApiClientProperties;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final ExternalApiClientProperties externalApiClientProperties;

    public RestTemplateConfig(ExternalApiClientProperties externalApiClientProperties) {
        this.externalApiClientProperties = externalApiClientProperties;
    }

    @Bean
    public RestTemplate restTemplate() {
        int connectTimeout = externalApiClientProperties.getConnectTimeout(); // 5 秒
        int readTimeout = externalApiClientProperties.getReadTimeout();   // 10 秒

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout)
                .setSocketTimeout(readTimeout)
                .build();

        CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build();

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(client);

        return new RestTemplate(factory);
    }
}
