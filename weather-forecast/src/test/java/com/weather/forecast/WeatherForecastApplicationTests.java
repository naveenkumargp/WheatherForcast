package com.weather.forecast;

import com.weather.forecast.model.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * validates all integration test flow of the application by calling atypes of ll urls
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WeatherForecastApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * validates happy flow with correct URI
     */
    @Test
    public void getWeatherStatus() {
        ResponseEntity<WeatherResponse> response = this.restTemplate.getForEntity("http://localhost:" + port + "/weather/current?zipCode=560040", WeatherResponse.class);
        assertThat(response.getStatusCode().value()==HttpStatus.OK.value());
    }

    /**
     * validates not found weather forecast status for invalid zip code
     */
    @Test
    public void getNotFoundWeatherStatus() {
        ResponseEntity<WeatherResponse> response = this.restTemplate.getForEntity("http://localhost:" + port + "/weather/current?zipCode=5600", WeatherResponse.class);
        assertThat(response.getStatusCode().value()==HttpStatus.NOT_FOUND.value());
    }

    /**
     * validates bad request  weather forecast status for empty zip code
     */
    @Test
    public void getBadWeatherStatus() {
        ResponseEntity<WeatherResponse> response = this.restTemplate.getForEntity("http://localhost:" + port + "/weather/current?zipCode=", WeatherResponse.class);
        assertThat(response.getStatusCode().value()==HttpStatus.BAD_REQUEST.value());
    }
}
