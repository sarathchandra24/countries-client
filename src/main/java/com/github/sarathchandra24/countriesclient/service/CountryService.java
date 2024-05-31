package com.github.sarathchandra24.countriesclient.service;

import com.github.sarathchandra24.countriesclient.model.Country;
import com.github.sarathchandra24.countriesclient.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class CountryService implements ApplicationListener<ApplicationReadyEvent> {

    private final CountryRepository countryRepository;
    private final WebClient webClient;

    @Value("${graphql.client.url}")
    private String graphqlUrl;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.webClient = WebClient.builder().build();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        loadCountries();
    }

    public void loadCountries() {
        String query = loadQueryFromFile("graphql/countriesQuery.graphql");

        try {
            Map<String, Object> response = webClient.post()
                    .uri(graphqlUrl)
                    .bodyValue(Map.of("query", query))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<Map<String, String>> countries = (List<Map<String, String>>) ((Map<String, Object>) response.get("data")).get("countries");

            countries.forEach(countryData -> {
                Country country = new Country(
                        null, // id will be generated
                        countryData.get("code"),
                        countryData.get("name"),
                        countryData.get("emoji"),
                        countryData.get("currency"),
                        countryData.get("capital")
                );

                countryRepository.save(country);
            });

        } catch (WebClientResponseException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    public String loadQueryFromFile(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load GraphQL query from file", e);
        }
    }

    public Country getCountryByCode(String code) {
        return countryRepository.findByCode(code).orElse(null);
    }
}