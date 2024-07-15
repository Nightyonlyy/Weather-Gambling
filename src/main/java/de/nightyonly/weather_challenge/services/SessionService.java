package de.nightyonly.weather_challenge.services;

import de.nightyonly.weather_challenge.model.Sessions;
import de.nightyonly.weather_challenge.model.Users;
import de.nightyonly.weather_challenge.repos.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService {

    private final SessionsRepository sessionsRepository;

    @Autowired
    public SessionService(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    public void createSession(Users user) {
        Sessions session = new Sessions();
        session.setUser(user);
        session.setValidFrom(LocalDateTime.now());
        session.setValidTo(LocalDateTime.now().plusHours(24*14));
        sessionsRepository.save(session);
    }

    public void updateSessionValidTo(String username) {
        // Find the most recent session for the user and update the valid_to field
        Sessions session = sessionsRepository.findFirstByUserUsernameOrderByValidFromDesc(username);
        if (session != null) {
            session.setValidTo(LocalDateTime.now());
            sessionsRepository.save(session);
        }
    }
}
