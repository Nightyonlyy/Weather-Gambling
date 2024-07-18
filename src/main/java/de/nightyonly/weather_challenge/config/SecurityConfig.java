package de.nightyonly.weather_challenge.config;

import de.nightyonly.weather_challenge.services.CustomOAuth2UserService;
import de.nightyonly.weather_challenge.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final SessionService sessionService;

    @Autowired
    public SecurityConfig(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/search").authenticated()
                        .requestMatchers("/dashboard").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserServiceBean())
                        )
                        .defaultSuccessUrl("/search", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }


    @Bean
    public DefaultOAuth2UserService customOAuth2UserServiceBean() {
        return new CustomOAuth2UserService();
    }
}

