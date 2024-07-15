package de.nightyonly.weather_challenge.services;

import de.nightyonly.weather_challenge.model.Users;
import de.nightyonly.weather_challenge.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUserCity(String username, String city, Double latitude, Double longitude) {
        Optional<Users> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setCity(city);
            user.setLatitude(latitude);
            user.setLongitude(longitude);
            userRepository.save(user);
        }
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

