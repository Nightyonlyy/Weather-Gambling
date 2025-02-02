package de.nightyonly.weather_challenge.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.nightyonly.weather_challenge.model.pojo.Location;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherDTO {

    @JsonProperty("time")
    private String time;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("windspeed")
    private double windSpeed;

    @JsonProperty("location")
    private LocationDTO location;

}

