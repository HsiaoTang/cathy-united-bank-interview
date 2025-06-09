package com.cub.interview.cathyunitedbankinterview.service;

import com.cub.interview.cathyunitedbankinterview.bean.dto.request.ExchangeRateCreateRequest;
import com.cub.interview.cathyunitedbankinterview.bean.dto.request.ExchangeRateUpdateRequest;
import com.cub.interview.cathyunitedbankinterview.bean.dto.response.ExchangeRateSourceResponse;
import com.cub.interview.cathyunitedbankinterview.bean.entity.ExchangeRate;
import com.cub.interview.cathyunitedbankinterview.config.properties.ExchangeRateSourceProperties;
import com.cub.interview.cathyunitedbankinterview.repository.ExchangeRateRepository;
import com.cub.interview.cathyunitedbankinterview.util.ExternalApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@Slf4j
public class ExchangeRateService {

    private final ExchangeRateSourceProperties exchangeRateSourceProperties;
    private final ExternalApiUtil externalApiUtil;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(
            ExchangeRateSourceProperties exchangeRateSourceProperties,
            ExternalApiUtil externalApiUtil,
            ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateSourceProperties = exchangeRateSourceProperties;
        this.externalApiUtil = externalApiUtil;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeRateSourceResponse getExchangeRateFromSource() {
        return externalApiUtil.get(
                exchangeRateSourceProperties.getUrl(),
                ExchangeRateSourceResponse.class,
                null,
                null
        );
    }

    public ExchangeRate createExchangeRate(ExchangeRateCreateRequest exchangeRateCreateRequest) {
        return exchangeRateRepository.save(
                ExchangeRate.fromExchangeRateCreateRequest(exchangeRateCreateRequest)
        );
    }

    public ExchangeRate getExchangeRate(UUID id) {
        return exchangeRateRepository.findById(id).orElse(null);
    }

    public ExchangeRate updateExchangeRate(UUID id, ExchangeRateUpdateRequest exchangeRateUpdateRequest) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exchange rate not found with id: %s", id)));
        if (exchangeRateUpdateRequest.getRate() != null) {
            exchangeRate.setRate(exchangeRateUpdateRequest.getRate());
        }
        if(exchangeRateUpdateRequest.getRateTime() != null) {
            exchangeRate.setRateTime(exchangeRateUpdateRequest.getRateTime());
        }
        return exchangeRateRepository.save(exchangeRate);
    }

    public void deleteExchangeRate(UUID id) {
        exchangeRateRepository.deleteById(id);
    }
}
