package de.nightyonly.weather_challenge.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyWeatherDTO {

    @JsonProperty("time")
    private List<String> times;

    @JsonProperty("temperature_2m")
    private List<Double> temperatures;

    @JsonProperty("relative_humidity_2m")
    private List<Double> humidities;

    @JsonProperty("wind_speed_10m")
    private List<Double> windSpeeds;
}
