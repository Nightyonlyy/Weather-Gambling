package de.nightyonly.weather_challenge.services;

import de.nightyonly.weather_challenge.payload.CurrentWeatherDTO;
import de.nightyonly.weather_challenge.payload.DailyWeatherDTO;
import de.nightyonly.weather_challenge.payload.HourlyWeatherDTO;
import de.nightyonly.weather_challenge.payload.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public CurrentWeatherDTO getCurrentWeather(String latitude, String longitude) {
        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true&timezone=Europe/Berlin", latitude, longitude);
        logger.info("Requesting current weather data from URL: " + url);

        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

        return weatherResponse.getCurrentWeather();
    }

    public HourlyWeatherDTO getHourlyWeather(String latitude, String longitude) {
        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m&timezone=Europe/Berlin", latitude, longitude);
        logger.info("Requesting hourly weather data from URL: " + url);
        return restTemplate.getForObject(url, HourlyWeatherDTO.class);
    }

    public DailyWeatherDTO getDailyWeather(String latitude, String longitude) {
        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=temperature_2m_max,temperature_2m_min&timezone=Europe/Berlin", latitude, longitude);
        logger.info("Requesting daily weather data from URL: " + url);
        return restTemplate.getForObject(url, DailyWeatherDTO.class);
    }
}



