package de.nightyonly.weather_challenge.controller.rest;

import de.nightyonly.weather_challenge.model.Users;
import de.nightyonly.weather_challenge.payload.CurrentWeatherDTO;
import de.nightyonly.weather_challenge.payload.DailyWeatherDTO;
import de.nightyonly.weather_challenge.payload.HourlyWeatherDTO;
import de.nightyonly.weather_challenge.services.UserService;
import de.nightyonly.weather_challenge.services.WeatherService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;


@RequestMapping("/api/weather" )
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    private final UserService userService;

    public WeatherController(WeatherService weatherService, UserService userService) {
        this.weatherService = weatherService;
        this.userService = userService;
    }

    @GetMapping("/current")
    public CurrentWeatherDTO getCurrentWeather(Authentication authentication) {
        Users user = userService.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return weatherService.getCurrentWeather(authentication.getName());
    }

    @GetMapping("/hourly")
    public HourlyWeatherDTO getHourlyWeather(Authentication authentication) {
        Users user = userService.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return weatherService.getHourlyWeather(String.valueOf(user.getLatitude()), String.valueOf(user.getLongitude()));
    }

    @GetMapping("/daily")
    public DailyWeatherDTO getDailyWeather(Authentication authentication) {
        Users user = userService.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return weatherService.getDailyWeather(String.valueOf(user.getLatitude()), String.valueOf(user.getLongitude()));
    }
}



