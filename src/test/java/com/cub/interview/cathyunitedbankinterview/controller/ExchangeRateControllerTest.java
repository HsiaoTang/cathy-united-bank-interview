package com.cub.interview.cathyunitedbankinterview.controller;

import com.cub.interview.cathyunitedbankinterview.bean.dto.request.ExchangeRateCreateRequest;
import com.cub.interview.cathyunitedbankinterview.bean.dto.request.ExchangeRateUpdateRequest;
import com.cub.interview.cathyunitedbankinterview.bean.dto.response.ExchangeRateSourceResponse;
import com.cub.interview.cathyunitedbankinterview.bean.entity.ExchangeRate;
import com.cub.interview.cathyunitedbankinterview.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    void testGetExchangeRateFromSource() throws Exception {
        ExchangeRateSourceResponse mockResponse = buildMockExchangeRateSourceResponse();

        when(exchangeRateService.getExchangeRateFromSource()).thenReturn(mockResponse);

        mockMvc.perform(get("/v1/exchangeRate/source"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.disclaimer").value("just for test"))
                .andExpect(jsonPath("$.data.chartName").value("Bitcoin"))
                .andExpect(jsonPath("$.data.time.updated").value("Sep 2, 2024 07:07:20 UTC"))
                .andExpect(jsonPath("$.data.bpi.USD.rate_float").value(57756.2984))
                .andExpect(jsonPath("$.data.bpi.EUR.code").value("EUR"));
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

    @Test
    void testTransformFromExchangeRateSourceResponse() throws Exception {
        ExchangeRateSourceResponse input = buildMockExchangeRateSourceResponse();
        String requestJson = new ObjectMapper().writeValueAsString(input);

        mockMvc.perform(post("/v1/exchangeRate/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].currencyCode").value("EUR"))
                .andExpect(jsonPath("$.data[0].rate").value(52243.2865))
                .andExpect(jsonPath("$.data[2].currencyCode").value("USD"))
                .andExpect(jsonPath("$.data[2].currencyNameZh").value("美元"));
    }

    private List<ExchangeRate> buildMockExchangeRateList() {
        List<ExchangeRate> mockResult = new ArrayList<>();

        LocalDateTime now = LocalDateTime.of(2025, 6, 6, 10, 0, 0, 0);
        LocalDateTime rateTime = LocalDateTime.of(2024, 9, 2, 15, 7, 20);

        ExchangeRate usd = new ExchangeRate();
        usd.setId(null);
        usd.setCreatedAt(now);
        usd.setUpdatedAt(now);
        usd.setCurrencyCode("USD");
        usd.setCurrencyNameZh("美元");
        usd.setCurrencySymbol("&#36;");
        usd.setRate(BigDecimal.valueOf(57756.2984));
        usd.setDescription("United States Dollar");
        usd.setRateTime(rateTime);

        ExchangeRate gbp = new ExchangeRate();
        gbp.setId(null);
        gbp.setCreatedAt(now);
        gbp.setUpdatedAt(now);
        gbp.setCurrencyCode("GBP");
        gbp.setCurrencyNameZh("英鎊");
        gbp.setCurrencySymbol("&pound;");
        gbp.setRate(BigDecimal.valueOf(43984.0203));
        gbp.setDescription("British Pound Sterling");
        gbp.setRateTime(rateTime);

        ExchangeRate eur = new ExchangeRate();
        eur.setId(null);
        eur.setCreatedAt(now);
        eur.setUpdatedAt(now);
        eur.setCurrencyCode("EUR");
        eur.setCurrencyNameZh("歐元");
        eur.setCurrencySymbol("&euro;");
        eur.setRate(BigDecimal.valueOf(52243.2865));
        eur.setDescription("Euro");
        eur.setRateTime(rateTime);

        mockResult.add(usd);
        mockResult.add(gbp);
        mockResult.add(eur);
        return mockResult;
    }

    @Test
    void testCreateExchangeRate() throws Exception {
        ExchangeRateCreateRequest request = new ExchangeRateCreateRequest();
        request.setCurrencyCode("JPY");
        request.setCurrencyNameZh("日圓");
        request.setCurrencySymbol("¥");
        request.setRate(BigDecimal.valueOf(157.38));
        request.setDescription("Japanese Yen");
        request.setRateTime(LocalDateTime.parse("2024-09-02T15:07:20"));

        ExchangeRate mockExchangeRate = new ExchangeRate();
        mockExchangeRate.setId(UUID.randomUUID());
        mockExchangeRate.setCurrencyCode("JPY");
        mockExchangeRate.setCurrencyNameZh("日圓");
        mockExchangeRate.setCurrencySymbol("¥");
        mockExchangeRate.setRate(BigDecimal.valueOf(157.38));
        mockExchangeRate.setDescription("Japanese Yen");
        mockExchangeRate.setRateTime(LocalDateTime.parse("2024-09-02T15:07:20"));
        mockExchangeRate.setCreatedAt(LocalDateTime.now());
        mockExchangeRate.setUpdatedAt(LocalDateTime.now());

        when(exchangeRateService.createExchangeRate(any(ExchangeRateCreateRequest.class)))
                .thenReturn(mockExchangeRate);

        objectMapper.registerModule(new JavaTimeModule());
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/v1/exchangeRate/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.currencyCode").value("JPY"))
                .andExpect(jsonPath("$.data.currencyNameZh").value("日圓"));
    }

    @Test
    void testReadExchangeRate() throws Exception {
        UUID id = UUID.randomUUID();

        ExchangeRate mockExchangeRate = new ExchangeRate();
        mockExchangeRate.setId(id);
        mockExchangeRate.setCurrencyCode("JPY");
        mockExchangeRate.setCurrencyNameZh("日圓");
        mockExchangeRate.setRate(BigDecimal.valueOf(155.25));
        mockExchangeRate.setRateTime(LocalDateTime.parse("2024-09-02T15:07:20"));
        mockExchangeRate.setCreatedAt(LocalDateTime.now());
        mockExchangeRate.setUpdatedAt(LocalDateTime.now());

        when(exchangeRateService.getExchangeRate(id)).thenReturn(mockExchangeRate);

        mockMvc.perform(get("/v1/exchangeRate/read")
                        .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.currencyCode").value("JPY"));
    }

    @Test
    void testUpdateExchangeRate() throws Exception {
        UUID id = UUID.randomUUID();

        ExchangeRateUpdateRequest updateRequest = new ExchangeRateUpdateRequest();
        updateRequest.setRate(BigDecimal.valueOf(156.78));
        updateRequest.setRateTime(LocalDateTime.parse("2024-09-03T12:00:00"));

        ExchangeRate updatedExchangeRate = new ExchangeRate();
        updatedExchangeRate.setId(id);
        updatedExchangeRate.setCurrencyCode("JPY");
        updatedExchangeRate.setCurrencyNameZh("日圓");
        updatedExchangeRate.setRate(BigDecimal.valueOf(156.78));
        updatedExchangeRate.setRateTime(LocalDateTime.parse("2024-09-03T12:00:00"));
        updatedExchangeRate.setCreatedAt(LocalDateTime.now());
        updatedExchangeRate.setUpdatedAt(LocalDateTime.now());

        when(exchangeRateService.updateExchangeRate(eq(id), any(ExchangeRateUpdateRequest.class)))
                .thenReturn(updatedExchangeRate);

        String json = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/v1/exchangeRate/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.rate").value("156.78"))
                .andExpect(jsonPath("$.data.rateTime").value("2024-09-03T12:00:00"));
    }
}
