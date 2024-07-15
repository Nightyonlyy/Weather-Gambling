package de.nightyonly.weather_challenge.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nightyonly.weather_challenge.model.pojo.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/location")
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/search")
    public ResponseEntity<List<Location>> search(@RequestParam String query, Authentication authentication) throws IOException {

        if(authentication == null ||!authentication.isAuthenticated()  || authentication.getAuthorities().isEmpty()){
            return ResponseEntity.status(401).body(null);
        }

        if (query == null || query.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        String urlQuery = "https://nominatim.openstreetmap.org/search?q=" + query + "&format=json";
        String urlPostalCode = "https://nominatim.openstreetmap.org/search?postalcode=" + query + "&format=json";

        String responseQuery = restTemplate.getForObject(urlQuery, String.class);
        String responsePostalCode = restTemplate.getForObject(urlPostalCode, String.class);

        List<Location> resultsQuery = objectMapper.readValue(responseQuery, new TypeReference<List<Location>>() {});
        List<Location> resultsPostalCode = objectMapper.readValue(responsePostalCode, new TypeReference<List<Location>>() {});

        // Merging results and removing duplicates
        List<Location> mergedResults = mergeResults(resultsQuery, resultsPostalCode);

        return ResponseEntity.ok(mergedResults);
    }

    private List<Location> mergeResults(List<Location> resultsQuery, List<Location> resultsPostalCode) {
        // Combine both lists and remove duplicates based on the unique properties of the Location class
        List<Location> combinedResults = new ArrayList<>(resultsQuery);
        for (Location loc : resultsPostalCode) {
            if (!combinedResults.contains(loc)) {
                combinedResults.add(loc);
            }
        }
        return combinedResults;
    }
}

