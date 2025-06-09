package com.cub.interview.cathyunitedbankinterview.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CurrencyNameMappingUtil {

    private static final Map<String, String> currencyNameZhMap = new HashMap<>();

    static {
        try (InputStream inputStream = CurrencyNameMappingUtil.class
                .getClassLoader()
                .getResourceAsStream("currency_name_map.json")) {
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule());
            currencyNameZhMap.putAll(objectMapper.readValue(inputStream, new TypeReference<Map<String, String>>() {}));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load currency map", e);
        }
    }

    public static String getNameZh(String code) {
        return currencyNameZhMap.getOrDefault(code, "未知幣別");
    }

}
