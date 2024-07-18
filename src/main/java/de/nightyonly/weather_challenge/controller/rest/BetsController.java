package de.nightyonly.weather_challenge.controller.rest;

import de.nightyonly.weather_challenge.model.Bets;
import de.nightyonly.weather_challenge.payload.BetDTO;
import de.nightyonly.weather_challenge.services.BetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bets")
public class BetsController {

    @Autowired
    private BetsService betsService;

//    @PostMapping("/place")
//    public ResponseEntity<String> placeBet(@RequestBody BetDTO betDTO, @RequestParam Long userId) {
//        try {
//            Bets savedBet = betsService.placeBet(betDTO);
//            return ResponseEntity.ok("Bet placed successfully: " + savedBet.getBetUuid());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/active")
//    public List<Bets> getActiveBets() {
//        return betsService.getActiveBets();
//    }
}
