package com.cub.interview.cathyunitedbankinterview.bean.entity;

import com.cub.interview.cathyunitedbankinterview.bean.dto.request.ExchangeRateCreateRequest;
import com.cub.interview.cathyunitedbankinterview.bean.dto.response.ExchangeRateSourceResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangeRateTest {

    @Test
    void testFromExchangeRateSourceResponse() {
        ExchangeRateSourceResponse exchangeRateSourceResponse = buildMockExchangeRateSourceResponse();
        List<ExchangeRate> result = ExchangeRate.fromExchangeRateSourceResponse(exchangeRateSourceResponse);

        assertThat(result).hasSize(3);

        ExchangeRate usd = result.stream().filter(r -> r.getCurrencyCode().equals("USD")).findFirst().orElse(null);
        assertThat(usd).isNotNull();
        assertThat(usd.getRate().doubleValue()).isEqualTo(57756.2984);

        ExchangeRate gbp = result.stream().filter(r -> r.getCurrencyCode().equals("GBP")).findFirst().orElse(null);
        assertThat(gbp).isNotNull();
        assertThat(gbp.getRate().doubleValue()).isEqualTo( 43984.0203);

        ExchangeRate eur = result.stream().filter(r -> r.getCurrencyCode().equals("EUR")).findFirst().orElse(null);
        assertThat(eur).isNotNull();
        assertThat(eur.getRate().doubleValue()).isEqualTo(52243.2865);
    }

    private ExchangeRateSourceResponse buildMockExchangeRateSourceResponse() {
        ExchangeRateSourceResponse response = new ExchangeRateSourceResponse();

        ExchangeRateSourceResponse.Time time = new ExchangeRateSourceResponse.Time();
        time.setUpdated("Sep 2, 2024 07:07:20 UTC");
        time.setUpdatedISO("2024-09-02T07:07:20+00:00");
        time.setUpdatedUK("Sep 2, 2024 at 08:07 BST");
        response.setTime(time);

        response.setDisclaimer("just for test");
        response.setChartName("Bitcoin");

        Map<String, ExchangeRateSourceResponse.CurrencyInfo> bpi = new HashMap<>();

        ExchangeRateSourceResponse.CurrencyInfo usd = new ExchangeRateSourceResponse.CurrencyInfo();
        usd.setCode("USD");
        usd.setSymbol("&#36;");
        usd.setRate("57,756.298");
        usd.setDescription("United States Dollar");
        usd.setRateFloat(57756.2984);
        bpi.put("USD", usd);

        ExchangeRateSourceResponse.CurrencyInfo gbp = new ExchangeRateSourceResponse.CurrencyInfo();
        gbp.setCode("GBP");
        gbp.setSymbol("&pound;");
        gbp.setRate("43,984.02");
        gbp.setDescription("British Pound Sterling");
        gbp.setRateFloat(43984.0203);
        bpi.put("GBP", gbp);

        ExchangeRateSourceResponse.CurrencyInfo eur = new ExchangeRateSourceResponse.CurrencyInfo();
        eur.setCode("EUR");
        eur.setSymbol("&euro;");
        eur.setRate("52,243.287");
        eur.setDescription("Euro");
        eur.setRateFloat(52243.2865);
        bpi.put("EUR", eur);

        response.setBpi(bpi);

        return response;
    }
}
