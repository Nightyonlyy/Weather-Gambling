package de.nightyonly.weather_challenge.model;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_user_uuid", updatable = false, nullable = false)
    private UUID userUuid;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "coins")
    private Integer coins;

    @Column(name ="cityname")
    private String city;

    @Column(name = "langitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

}
