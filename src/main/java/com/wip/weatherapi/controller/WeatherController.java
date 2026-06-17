package com.wip.weatherapi.controller;

import com.wip.weatherapi.dto.WeatherResponse;
import com.wip.weatherapi.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public WeatherResponse getCurrentWeather(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return weatherService.getCurrentWeather(lat, lon);
    }
}
