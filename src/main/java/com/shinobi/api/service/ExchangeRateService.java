package com.shinobi.api.service;

import com.shinobi.api.model.ExchangeRateResponse;
import com.shinobi.api.model.Rate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExchangeRateService {

    private static final String BASE_URL = "http://api.nbp.pl/api/exchangerates/rates/";

    public double getAverageExchangeRate(String currencyCode, LocalDate date) {
        String url = BASE_URL + "a/" + currencyCode + "/" + date.toString() + "/";
        ExchangeRateResponse response = getExchangeRateResponse(url);
        if(response.getRates().isEmpty()) {
            throw new RuntimeException("No exchange rates available for currency " + currencyCode + " on date " + date.toString());
        }
        return response.getRates().get(0).getMid();
    }

    public double[] getMaxMinAverageValues(String currencyCode, int numberOfQuotations) {
        if (numberOfQuotations <= 0) {
            throw new IllegalArgumentException("numberOfQuotations must be a positive integer: int > 0");
        }
        String url = BASE_URL + "a/" + currencyCode + "/last/" + numberOfQuotations + "/";
        ExchangeRateResponse response = getExchangeRateResponse(url);
        List<Rate> rates = response.getRates();
        if (rates.isEmpty()) {
            throw new RuntimeException("No exchange rates available for currency " + currencyCode);
        }
        int size = rates.size();

        if (size < 2) {
            throw new RuntimeException("Exchange rates not available for calculation. Available rates: " + rates.size());
        }

        double[] result = new double[2];
        double max =  rates.get(0).getMid();
        double rate, min;
        min = size > 1 ? rates.get(0).getMid() : 0;

        for (int i = 1; i < size; i++) {
            rate = rates.get(i).getMid();

            if (rate > max) {
                max = rate;
            }
            if (rate < min) {
                min = rate;
            }
        }
        // result[0] -> max and result[1] -> min
        result[0] = max;
        result[1] = min;

        return result;
    }

    public double getMajorDifference(String currencyCode, int numberOfQuotations) {
        String url = BASE_URL + "c/" + currencyCode + "/last/" + numberOfQuotations + "/";
        if (numberOfQuotations == 0) {
            return 0;
        }
        ExchangeRateResponse response = getExchangeRateResponse(url);
        List<Rate> rates = response.getRates();
        int size = rates.size();
        double maxDifference = 0;

        try {
            for (int i = 0; i < size; i++) {
                double difference = Math.abs(rates.get(i).getAsk() - rates.get(i).getBid());
                maxDifference = Math.max(maxDifference, difference);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while calculating: " + e.getMessage());
        }
        return maxDifference;
    }

    private ExchangeRateResponse getExchangeRateResponse(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ExchangeRateResponse.class);
    }

    public double getBid(String currencyCode, int numberOfQuotations) {
        String url = BASE_URL + "c/" + currencyCode + "/last/" + numberOfQuotations + "/";
        ExchangeRateResponse response = getExchangeRateResponse(url);
        return response.getRates().get(0).getBid();
    }

    public double getAsk(String currencyCode, int numberOfQuotations) {
        String url = BASE_URL + "c/" + currencyCode + "/last/" + numberOfQuotations + "/";
        ExchangeRateResponse response = getExchangeRateResponse(url);
        return response.getRates().get(0).getAsk();
    }

}
