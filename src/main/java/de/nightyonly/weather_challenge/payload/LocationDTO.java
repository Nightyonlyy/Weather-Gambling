package de.nightyonly.weather_challenge.payload;

import lombok.Data;

@Data
public class LocationDTO {
    private String city;
    private double latitude;
    private double longitude;
}
