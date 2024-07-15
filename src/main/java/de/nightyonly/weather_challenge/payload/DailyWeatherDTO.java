package de.nightyonly.weather_challenge.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeatherDTO {

    @JsonProperty("time")
    private List<String> times;

    @JsonProperty("temperature_2m_max")
    private List<Double> maxTemperatures;

    @JsonProperty("temperature_2m_min")
    private List<Double> minTemperatures;

//    @JsonProperty("relative_humidity_2m")
//    private List<Double> humidities;
//
//    @JsonProperty("wind_speed_10m")
//    private List<Double> windSpeeds;
}

