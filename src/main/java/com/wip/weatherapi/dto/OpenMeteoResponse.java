package com.wip.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenMeteoResponse(
        Current current
) {
    public record Current(
            @JsonProperty("temperature_2m") Double temperature,
            @JsonProperty("relative_humidity_2m") Integer humidity,
            @JsonProperty("wind_speed_10m") Double windSpeed,
            @JsonProperty("weather_code") Integer weatherCode
    ) {
    }
}
