package de.nightyonly.weather_challenge.controller;

import de.nightyonly.weather_challenge.model.Users;
import de.nightyonly.weather_challenge.services.SessionService;
import de.nightyonly.weather_challenge.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;


@Controller
public class DefaultController {

    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public DefaultController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "pages/landing/landing";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/auth/auth";
    }



    @GetMapping("/search")
    public String search(Model model, OAuth2AuthenticationToken authentication) {

        if(authentication == null ||!authentication.isAuthenticated()  || authentication.getAuthorities().isEmpty()){
            return "redirect:/login";
        }

        Map<String, Object> userAttributes = authentication.getPrincipal().getAttributes();

        // Log all attributes
        for (Map.Entry<String, Object> entry : userAttributes.entrySet()) {
            logger.info("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        Optional<Users> optionalUser = userService.findByUsername(authentication.getName());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            model.addAttribute("user", userAttributes);
            model.addAttribute("city", user.getCity());
            model.addAttribute("latitude", user.getLatitude());
            model.addAttribute("longitude", user.getLongitude());
        }

        return "pages/search/search";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, OAuth2AuthenticationToken authentication) {

        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
            return "redirect:/login";
        }

        Map<String, Object> userAttributes = authentication.getPrincipal().getAttributes();
        System.out.println("User attributes: " + userAttributes);

        Optional<Users> optionalUser = userService.findByUsername(authentication.getName());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            model.addAttribute("user", userAttributes);
            model.addAttribute("city", user.getCity());
            model.addAttribute("latitude", user.getLatitude());
            model.addAttribute("longitude", user.getLongitude());
        }

        return "pages/dashboard/dashboard";
    }




    @GetMapping("/logout")
    public String logout (HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            sessionService.updateSessionValidTo(authentication.getName());
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}






