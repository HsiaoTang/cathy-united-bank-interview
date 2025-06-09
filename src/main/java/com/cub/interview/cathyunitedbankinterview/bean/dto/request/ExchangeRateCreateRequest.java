package com.cub.interview.cathyunitedbankinterview.bean.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExchangeRateCreateRequest {

    private String currencyCode;

    private String currencyNameZh;

    private String currencySymbol;

    private BigDecimal rate;

    private String description;

    private LocalDateTime rateTime;
}
