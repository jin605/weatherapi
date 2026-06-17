package com.wip.weatherapi.service;

import com.wip.weatherapi.dto.OpenMeteoResponse;
import com.wip.weatherapi.dto.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    public WeatherResponse getCurrentWeather(double lat, double lon) {
        OpenMeteoResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("current", "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m")
                        .queryParam("timezone", "Asia/Seoul")
                        .build())
                .retrieve()
                .bodyToMono(OpenMeteoResponse.class)
                .block();

        if (response == null || response.current() == null) {
            throw new IllegalStateException("Weather API response is empty.");
        }

        OpenMeteoResponse.Current current = response.current();

        return new WeatherResponse(
                lat,
                lon,
                current.temperature(),
                current.humidity(),
                current.windSpeed(),
                current.weatherCode(),
                "open-meteo"
        );
    }
}
