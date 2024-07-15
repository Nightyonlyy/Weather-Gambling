package de.nightyonly.weather_challenge.services;

import de.nightyonly.weather_challenge.model.Users;
import de.nightyonly.weather_challenge.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private SessionService sessionService;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = null;
        String username = null;
        String profilePic = null;

        if ("github".equals(registrationId)) {
            email = (String) attributes.get("email");
            username = (String) attributes.get("login");
            profilePic = (String) attributes.get("avatar_url");

            // Fallback to fetch email if not provided by GitHub
            if (email == null) {
                email = fetchGithubEmail(userRequest.getAccessToken().getTokenValue());
            }
        } else {
            email = (String) attributes.get("email");
            username = (String) attributes.get("name");
            profilePic = (String) attributes.get("picture");
        }

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        if (username == null) {
            username = email.substring(0, email.indexOf('@'));
        }

        String finalEmail = email;
        String finalUsername = username;
        String finalProfilePic = profilePic;
        Users user = userRepository.findByEmail(email).orElseGet(() -> {
            Users newUser = new Users();
            newUser.setEmail(finalEmail);
            newUser.setUsername(finalUsername);
            newUser.setProfilePic(finalProfilePic);
            return userRepository.save(newUser);
        });

        sessionService.createSession(user);

        Map<String, Object> updatedAttributes = new HashMap<>(oauth2User.getAttributes());
        updatedAttributes.put("name", username);

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("USER")),
                updatedAttributes,
                "name"
        );
    }

    private String fetchGithubEmail(String accessToken) {
        String url = "https://api.github.com/user/emails";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
            List<Map<String, Object>> emails = response.getBody();

            for (Map<String, Object> emailData : emails) {
                Boolean primary = (Boolean) emailData.get("primary");
                Boolean verified = (Boolean) emailData.get("verified");
                if (primary != null && primary && verified != null && verified) {
                    return (String) emailData.get("email");
                }
            }
        } catch (HttpClientErrorException e) {
            // Handle the exception as needed
            System.err.println("Error fetching GitHub email: " + e.getMessage());
        }

        return null;
    }
}
