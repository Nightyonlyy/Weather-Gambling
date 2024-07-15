package de.nightyonly.weather_challenge.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String display_name;
    private String lat;
    private String lon;

    // Override equals and hashCode methods to properly compare Location objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return display_name != null ? display_name.equals(location.display_name) : location.display_name == null;
    }

    @Override
    public int hashCode() {
        return display_name != null ? display_name.hashCode() : 0;
    }

}

