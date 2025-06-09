package com.cub.interview.cathyunitedbankinterview.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Component
public class ExternalApiUtil {

    private final RestTemplate restTemplate;

    public ExternalApiUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T get(
            String url,
            Class<T> responseType,
            Map<String, String> headers,
            Map<String, String> queryParams) {

        HttpHeaders httpHeaders = new HttpHeaders();
        Optional.ofNullable(headers).orElse(Collections.emptyMap())
                .forEach(httpHeaders::add);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        Optional.ofNullable(queryParams).orElse(Collections.emptyMap())
                .forEach(builder::queryParam);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                responseType
        ).getBody();
    }
}
