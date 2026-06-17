package com.wip.weatherapi.dto;

public record WeatherResponse(
        double latitude,
        double longitude,
        Double temperature,
        Integer humidity,
        Double windSpeed,
        Integer weatherCode,
        String source
) {
}
