package de.nightyonly.weather_challenge.repos;

import de.nightyonly.weather_challenge.model.Bets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BetRepository extends JpaRepository<Bets, UUID> {

}
