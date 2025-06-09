package com.cub.interview.cathyunitedbankinterview.bean.entity;

import com.cub.interview.cathyunitedbankinterview.bean.dto.request.ExchangeRateCreateRequest;
import com.cub.interview.cathyunitedbankinterview.bean.dto.response.ExchangeRateSourceResponse;
import com.cub.interview.cathyunitedbankinterview.util.CurrencyNameMappingUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "DOMAIN", name = "exchange_rate")
@Data
@NoArgsConstructor
public class ExchangeRate extends BaseEntity {

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "currency_name_zh", nullable = false)
    private String currencyNameZh;

    @Column(name = "currency_symbol")
    private String currencySymbol;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal rate;

    private String description;

    @Column(name = "rate_time", nullable = false)
    private LocalDateTime rateTime;

    public static List<ExchangeRate> fromExchangeRateSourceResponse(ExchangeRateSourceResponse exchangeRateSourceResponse) {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        String rateTimeISOStr = exchangeRateSourceResponse.getTime().getUpdatedISO();
        OffsetDateTime odt = OffsetDateTime.parse(rateTimeISOStr);
        ZonedDateTime taipeiZoned = odt.atZoneSameInstant(ZoneId.of("Asia/Taipei"));
        LocalDateTime localDateTimeISO = taipeiZoned.toLocalDateTime();
        exchangeRateSourceResponse.getBpi().forEach((currencyCode, currencyInfo) -> {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setCurrencyCode(currencyInfo.getCode());
            exchangeRate.setCurrencyNameZh(
                    CurrencyNameMappingUtil.getNameZh(
                            currencyInfo.getCode()
                    )
            );
            exchangeRate.setCurrencySymbol(currencyInfo.getSymbol());
            exchangeRate.setRate(BigDecimal.valueOf(currencyInfo.getRateFloat()));
            exchangeRate.setDescription(currencyInfo.getDescription());
            exchangeRate.setRateTime(localDateTimeISO);
            exchangeRate.setCreatedAt(LocalDateTime.now());
            exchangeRate.setUpdatedAt(LocalDateTime.now());
            exchangeRateList.add(exchangeRate);
        });
        return exchangeRateList;
    }

    public static ExchangeRate fromExchangeRateCreateRequest(ExchangeRateCreateRequest exchangeRateCreateRequest) {
        ExchangeRate exchangeRate = new ExchangeRate();
        BeanUtils.copyProperties(exchangeRateCreateRequest, exchangeRate);
        return exchangeRate;
    }
}
