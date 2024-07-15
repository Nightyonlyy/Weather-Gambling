package de.nightyonly.weather_challenge.controller.rest;

import de.nightyonly.weather_challenge.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@SuppressWarnings("unused") // this controller is used only for REST API endpoints and doesn't need to be instantiated directly. It's a Spring MVC controller.
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/updateCity")
    public ResponseEntity<Void> updateCity(@RequestParam String city, @RequestParam Double latitude, @RequestParam Double longitude, Authentication authentication) {

        if(authentication == null ||!authentication.isAuthenticated()  || authentication.getAuthorities().isEmpty()){
            return ResponseEntity.status(401).body(null);
        }

        String username = authentication.getName();
        userService.updateUserCity(username, city, latitude, longitude);
        return ResponseEntity.ok().build();
    }
}
