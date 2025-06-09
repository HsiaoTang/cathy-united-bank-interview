package com.cub.interview.cathyunitedbankinterview.bean.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExchangeRateUpdateRequest {

    private BigDecimal rate;

    private LocalDateTime rateTime;
}
