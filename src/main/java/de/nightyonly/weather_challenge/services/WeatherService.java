package de.nightyonly.weather_challenge.services;

import de.nightyonly.weather_challenge.model.Users;
import de.nightyonly.weather_challenge.payload.*;
import de.nightyonly.weather_challenge.repos.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;
    private final UsersRepository usersRepository;
    private final UserService userService;

    public WeatherService(RestTemplate restTemplate, UsersRepository usersRepository, UserService userService) {
        this.restTemplate = restTemplate;
        this.usersRepository = usersRepository;
        this.userService = userService;
    }

    public CurrentWeatherDTO getCurrentWeather(String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        double latitude = user.getLatitude();
        double longitude = user.getLongitude();

        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true&timezone=Europe/Berlin", latitude, longitude);
        logger.info("Requesting current weather data from URL: " + url);

        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
        CurrentWeatherDTO currentWeatherDTO = weatherResponse.getCurrentWeather();

        // Füge Location-Informationen hinzu
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setCity(user.getCity());
        locationDTO.setLatitude(user.getLatitude());
        locationDTO.setLongitude(user.getLongitude());

        currentWeatherDTO.setLocation(locationDTO);

        return currentWeatherDTO;
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



