package de.nightyonly.weather_challenge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bets")
public class Bets {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_bet_uuid", updatable = false, nullable = false)
    private UUID betUuid;

    @Column(name = "filter_timestamp", nullable = false)
    private LocalDateTime filter_timestamp;

    @Column(name = "ammount", nullable = false)
    private Integer ammount;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "location", nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    private Users user;
}
