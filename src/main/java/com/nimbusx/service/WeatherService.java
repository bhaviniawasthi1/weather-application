package com.nimbusx.service;

import com.nimbusx.model.ForecastDay;
import com.nimbusx.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private static final String CURRENT_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";
    private static final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&units=metric";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherResponse getWeather(String city) {
        Map<String, String> params = Map.of("city", city, "apiKey", apiKey);

        Map<String, Object> current = callApi(CURRENT_WEATHER_URL, params);
        Map<String, Object> forecastData = callApi(FORECAST_URL, params);

        return buildWeatherResponse(current, forecastData);
    }

    private Map<String, Object> callApi(String url, Map<String, String> params) {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class, params);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            String msg = e.getStatusText();
            try { msg = e.getResponseBodyAsString(); } catch (Exception ignored) {}
            throw new RuntimeException(msg);
        }
    }

    private WeatherResponse buildWeatherResponse(Map<String, Object> current, Map<String, Object> forecastData) {
        WeatherResponse response = new WeatherResponse();

        Map<String, Object> main = (Map<String, Object>) current.get("main");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) current.get("weather");
        Map<String, Object> weather = weatherList.get(0);
        Map<String, Object> wind = (Map<String, Object>) current.get("wind");

        response.setCity((String) current.get("name"));
        response.setTemperature((Double) main.get("temp"));
        response.setHumidity((Integer) main.get("humidity"));
        response.setDescription((String) weather.get("description"));
        response.setIcon((String) weather.get("icon"));
        response.setWindSpeed((Double) wind.get("speed"));

        response.setForecast(parseForecast(forecastData));
        return response;
    }

    private List<ForecastDay> parseForecast(Map<String, Object> forecastData) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) forecastData.get("list");

        Map<LocalDate, List<Map<String, Object>>> grouped = list.stream()
                .collect(Collectors.groupingBy(entry -> {
                    long timestamp = ((Number) entry.get("dt")).longValue();
                    return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
                }));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d");
        List<ForecastDay> forecast = new ArrayList<>();
        int daysAdded = 0;

        for (Map.Entry<LocalDate, List<Map<String, Object>>> entry : grouped.entrySet()) {
            if (daysAdded >= 5) break;

            List<Map<String, Object>> dayEntries = entry.getValue();
            double tempMin = dayEntries.stream()
                    .mapToDouble(e -> ((Number) ((Map<String, Object>) e.get("main")).get("temp_min")).doubleValue())
                    .min().orElse(0);
            double tempMax = dayEntries.stream()
                    .mapToDouble(e -> ((Number) ((Map<String, Object>) e.get("main")).get("temp_max")).doubleValue())
                    .max().orElse(0);
            int humidity = (int) dayEntries.stream()
                    .mapToInt(e -> (Integer) ((Map<String, Object>) e.get("main")).get("humidity"))
                    .average().orElse(0);
            Map<String, Object> midWeather = (Map<String, Object>) ((List<Map<String, Object>>) dayEntries.get(dayEntries.size() / 2).get("weather")).get(0);

            ForecastDay day = new ForecastDay();
            day.setDate(entry.getKey().format(formatter));
            day.setTempMin(Math.round(tempMin * 10.0) / 10.0);
            day.setTempMax(Math.round(tempMax * 10.0) / 10.0);
            day.setHumidity(humidity);
            day.setDescription((String) midWeather.get("description"));
            day.setIcon((String) midWeather.get("icon"));

            forecast.add(day);
            daysAdded++;
        }

        return forecast;
    }
}
