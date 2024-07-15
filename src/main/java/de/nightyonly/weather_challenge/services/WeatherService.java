package de.nightyonly.weather_challenge.services;

import de.nightyonly.weather_challenge.model.Users;
import de.nightyonly.weather_challenge.payload.*;
import de.nightyonly.weather_challenge.repos.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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
        String latitude = String.valueOf(user.getLatitude());
        String longitude = String.valueOf(user.getLongitude());

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


    public HourlyWeatherDTO getHourlyWeather(String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        String latitude = String.valueOf(user.getLatitude());
        String longitude = String.valueOf(user.getLongitude());

        // Set the start and end time to limit the forecast to the next 12 hours from the current hour
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(12);
        DateTimeFormatter urlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        String start = startTime.format(urlFormatter);
        String end = endTime.format(urlFormatter);

        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m&timezone=Europe/Berlin&start=%s&end=%s", latitude, longitude, start, end);
        logger.info("Requesting hourly weather data from URL: " + url);

        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
        assert weatherResponse != null;

        // Begrenzen der Daten auf die nächsten 12 Stunden
        HourlyWeatherDTO hourlyWeather = weatherResponse.getHourlyWeather();
        List<String> times = hourlyWeather.getTimes();
        List<Double> temperatures = hourlyWeather.getTemperatures();
        List<Double> humidities = hourlyWeather.getHumidities();
        List<Double> windSpeeds = hourlyWeather.getWindSpeeds();

        int startIndex = 0;
        for (int i = 0; i < times.size(); i++) {
            LocalDateTime time = LocalDateTime.parse(times.get(i), responseFormatter);
            if (!time.isBefore(startTime)) {
                startIndex = i;
                break;
            }
        }

        int endIndex = Math.min(startIndex + 12, times.size());

        hourlyWeather.setTimes(times.subList(startIndex, endIndex));
        hourlyWeather.setTemperatures(temperatures.subList(startIndex, endIndex));
        hourlyWeather.setHumidities(humidities.subList(startIndex, endIndex));
        hourlyWeather.setWindSpeeds(windSpeeds.subList(startIndex, endIndex));

        return hourlyWeather;
    }




//public DailyWeatherDTO getDailyWeather(String username) {
//    Users user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
//    String latitude = String.valueOf(user.getLatitude());
//    String longitude = String.valueOf(user.getLongitude());
//
//    String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=temperature_2m_max,temperature_2m_min,relative_humidity_2m,wind_speed_10m&timezone=Europe/Berlin&days=12", latitude, longitude);
//    logger.info("Requesting daily weather data from URL: " + url);
//
//    WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
//    DailyWeatherDTO dailyWeatherDTO = weatherResponse.getDailyWeather();
//
//    return dailyWeatherDTO;
//}
}



