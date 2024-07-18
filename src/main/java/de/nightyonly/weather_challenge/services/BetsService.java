package de.nightyonly.weather_challenge.services;

import de.nightyonly.weather_challenge.model.Bets;
import de.nightyonly.weather_challenge.model.Users;
import de.nightyonly.weather_challenge.payload.BetDTO;
import de.nightyonly.weather_challenge.repos.BetRepository;
import de.nightyonly.weather_challenge.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BetsService {

    @Autowired
    private BetRepository betsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WeatherService weatherService; // Für die Wetter-API-Integration

//    public Bets placeBet(BetDTO betDTO) {
//        Users user = usersRepository.findById(userUuid).orElseThrow(() -> new RuntimeException("User not found"));
//        if (user.getCoins() < betDTO.getAmmount()) {
//            throw new RuntimeException("Not enough coins");
//        }
//
//        user.setCoins(user.getCoins() - betDTO.getAmmount());
//        usersRepository.save(user);
//
//        Bets bet = new Bets();
//        bet.setUser(user);
//        bet.setFilter_timestamp(betDTO.getFilterTimestamp());
//        bet.setAmmount(betDTO.getAmmount());
//        bet.setTemperature(betDTO.getTemperature());
//        bet.setHumidity(betDTO.getHumidity());
//        bet.setWindSpeed(betDTO.getWindSpeed());
//        bet.setLocation(betDTO.getLocation());
//        bet.setIsCompleted(false);
//
//        return betsRepository.save(bet);
//    }
//
//    public List<Bets> getActiveBets() {
//        return betsRepository.findByIsCompletedFalseOrderByFilterTimestampDesc().stream().limit(6).collect(Collectors.toList());
//    }

//    @Scheduled(cron = "0 0 * * * *") // Scheduler, der jede Stunde läuft
//    public void evaluateBets() {
//        List<Bets> bets = betsRepository.findByIsCompletedFalseOrderByFilterTimestampDesc();
//        LocalDateTime now = LocalDateTime.now();
//
//        for (Bets bet : bets) {
//            if (bet.getFilter_timestamp().isBefore(now) || bet.getFilter_timestamp().isEqual(now)) {
//                WeatherData forecast = weatherService.getWeather(bet.getLocation(), bet.getFilterTimestamp());
//                Users user = bet.getUser();
//
//                if ((bet.getTemperature() != null && Math.abs(forecast.getTemperature() - bet.getTemperature()) <= 2) ||
//                        (bet.getHumidity() != null && Math.abs(forecast.getHumidity() - bet.getHumidity()) <= 5) ||
//                        (bet.getWindSpeed() != null && Math.abs(forecast.getWindSpeed() - bet.getWindSpeed()) <= 2)) {
//                    user.setCoins(user.getCoins() + bet.getAmmount() * 2);
//                }
//
//                bet.setIsCompleted(true);
//                usersRepository.save(user);
//                betsRepository.save(bet);
//            }
//        }
//    }

}

