package de.nightyonly.weather_challenge.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class BetDTO {


    @NotNull
    private LocalDateTime filterTimestamp;

    @NotNull
    private Integer ammount;

    private Double temperature;
    private Double humidity;
    private Double windSpeed;

    private String location;

}
