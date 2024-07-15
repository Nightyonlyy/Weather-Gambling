package de.nightyonly.weather_challenge.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private double latitude;
    private double longitude;
    private double elevation;
    private String timezone;
    private String timezone_abbreviation;

    @JsonProperty("current_weather")
    private CurrentWeatherDTO currentWeather;
}
