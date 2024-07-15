package de.nightyonly.weather_challenge.repos;

import de.nightyonly.weather_challenge.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionsRepository extends JpaRepository<Sessions, UUID> {

    Sessions findFirstByUserUsernameOrderByValidFromDesc(String username);


}