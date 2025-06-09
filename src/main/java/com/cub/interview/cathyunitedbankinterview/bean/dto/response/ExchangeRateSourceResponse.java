package com.cub.interview.cathyunitedbankinterview.bean.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
public class ExchangeRateSourceResponse {
    private Time time;
    private String disclaimer;
    private String chartName;
    private Map<String, CurrencyInfo> bpi;

    @Data
    public static class Time {
        private String updated;
        private String updatedISO;
        private String updatedUK;
    }

    @Data
    public static class CurrencyInfo {
        private String code;
        private String symbol;
        private String rate;
        private String description;

        @JsonProperty("rate_float")
        private double rateFloat;
    }
}
