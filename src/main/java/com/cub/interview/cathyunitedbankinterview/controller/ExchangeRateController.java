package com.cub.interview.cathyunitedbankinterview.controller;

import com.cub.interview.cathyunitedbankinterview.bean.dto.request.ExchangeRateCreateRequest;
import com.cub.interview.cathyunitedbankinterview.bean.dto.request.ExchangeRateUpdateRequest;
import com.cub.interview.cathyunitedbankinterview.bean.dto.response.DefaultResponse;
import com.cub.interview.cathyunitedbankinterview.bean.dto.response.ExchangeRateSourceResponse;
import com.cub.interview.cathyunitedbankinterview.bean.entity.ExchangeRate;
import com.cub.interview.cathyunitedbankinterview.service.ExchangeRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/exchangeRate")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/source")
    public ResponseEntity<DefaultResponse<ExchangeRateSourceResponse>> getExchangeRateFromSource() {
        return ResponseEntity.ok(
                DefaultResponse.success(exchangeRateService.getExchangeRateFromSource())
        );
    }

    @PostMapping("/transform")
    public ResponseEntity<DefaultResponse<List<ExchangeRate>>> transformFromExchangeRateSourceResponse(
            @RequestBody ExchangeRateSourceResponse exchangeRateSourceResponse
    ) {
        return ResponseEntity.ok(
                DefaultResponse.success(
                        ExchangeRate.fromExchangeRateSourceResponse(exchangeRateSourceResponse)
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<DefaultResponse<ExchangeRate>> createExchangeRate(
            @RequestBody ExchangeRateCreateRequest exchangeRateCreateRequest
            ) {
        return ResponseEntity.ok(
                DefaultResponse.success(
                        exchangeRateService.createExchangeRate(exchangeRateCreateRequest)
                )
        );
    }

    @GetMapping("/read")
    public ResponseEntity<DefaultResponse<ExchangeRate>> readExchangeRate(
            @RequestParam("id") UUID id
    ) {
        return ResponseEntity.ok(
                DefaultResponse.success(
                        exchangeRateService.getExchangeRate(id)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DefaultResponse<ExchangeRate>> updateExchangeRate(
            @PathVariable("id") UUID id,
            @RequestBody ExchangeRateUpdateRequest exchangeRateUpdateRequest
    ) {
        return ResponseEntity.ok(
                DefaultResponse.success(
                        exchangeRateService.updateExchangeRate(id, exchangeRateUpdateRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DefaultResponse<ExchangeRateSourceResponse>> deleteExchangeRate(
            @PathVariable("id") UUID id
    ) {
        exchangeRateService.deleteExchangeRate(id);
        return ResponseEntity.ok(
                DefaultResponse.success(
                        null
                )
        );
    }
}
