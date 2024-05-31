package com.github.sarathchandra24.countriesclient;

import com.github.sarathchandra24.countriesclient.repository.CountryRepository;
import com.github.sarathchandra24.countriesclient.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadQueryFromFile() throws IOException {
        // Given
        String path = "graphql/countriesQuery.graphql";
        ClassPathResource resource = new ClassPathResource(path);
        byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String expectedQuery = new String(data, StandardCharsets.UTF_8);

        // When
        String actualQuery = countryService.loadQueryFromFile(path);

        // Then
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    void testLoadQueryFromFileThrowsException() {
        // Given
        String path = "nonexistent/path.graphql";

        // When & Then
        assertThrows(RuntimeException.class, () -> countryService.loadQueryFromFile(path));
    }
}
