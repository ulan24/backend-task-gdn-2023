package com.shinobi.api.controller;

import com.shinobi.api.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/exchangerates/{currencyCode}/{date}")
    public ResponseEntity<String> getAverageExchangeRate(@PathVariable String date, @PathVariable String currencyCode) {
        double averageExchangeRate = exchangeRateService.getAverageExchangeRate(currencyCode, LocalDate.parse(date));
        String response = String.format("{\"currencyCode\":\"%s\",\"date\":\"%s\",\"exchangeRate\":%.4f}", currencyCode.toUpperCase(), date, averageExchangeRate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/maxminaverage/{currencyCode}/last/{numberOfQuotations}")
    public ResponseEntity<String> getMaxMinAverageValues(@PathVariable String currencyCode, @PathVariable int numberOfQuotations) {
        double[] maxMinAverageValues = exchangeRateService.getMaxMinAverageValues(currencyCode, numberOfQuotations);
        String response = String.format("{\"currencyCode\":\"%s\",\"max_average_value\":\"%.4f\",\"min_average_value\":\"%.4f\"}", currencyCode.toUpperCase(), maxMinAverageValues[0], maxMinAverageValues[1]);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/majordiff/{currencyCode}/last/{numberOfQuotations}")
    public ResponseEntity<String> getMajorDifference(@PathVariable String currencyCode, @PathVariable int numberOfQuotations) {
        double majorDifference = exchangeRateService.getMajorDifference(currencyCode, numberOfQuotations);
        double ask = exchangeRateService.getAsk(currencyCode, numberOfQuotations);
        double bid = exchangeRateService.getBid(currencyCode, numberOfQuotations);
        String response = String.format("{\"currencyCode\":\"%s\",\"bid\":\"%s\",\"ask\":\"%s\",\"major_difference\": \"%.4f\"}", currencyCode.toUpperCase(), bid, ask, majorDifference);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

