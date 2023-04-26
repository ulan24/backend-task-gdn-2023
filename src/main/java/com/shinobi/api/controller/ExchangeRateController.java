package com.shinobi.api.controller;

import com.shinobi.api.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/avg-rate/{currencyCode}/{date}")
    public ResponseEntity<Object> getAverageExchangeRate(@PathVariable String date, @PathVariable String currencyCode) {
        double averageExchangeRate = exchangeRateService.getAverageExchangeRate(currencyCode, LocalDate.parse(date));
        String response = String.format("{\"average_exchange_rate\": %.4f}", averageExchangeRate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/max-min/{currencyCode}/{numberOfQuotations}")
    public ResponseEntity<Object> getMaxMinAverageValues(@PathVariable String currencyCode, @PathVariable int numberOfQuotations) {
        double[] maxMinAverageValues = exchangeRateService.getMaxMinAverageValues(currencyCode, numberOfQuotations);
        String response = String.format("{\"max_average_value\": %.4f, \"min_average_value\": %.4f}", maxMinAverageValues[0], maxMinAverageValues[1]);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/diff/{currencyCode}/{numberOfQuotations}")
    public ResponseEntity<Object> getMajorDifference(@PathVariable String currencyCode, @PathVariable int numberOfQuotations) {
        double majorDifference = exchangeRateService.getMajorDifference(currencyCode, numberOfQuotations);
        String response = String.format("{\"major_difference\": %.4f}", majorDifference);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

