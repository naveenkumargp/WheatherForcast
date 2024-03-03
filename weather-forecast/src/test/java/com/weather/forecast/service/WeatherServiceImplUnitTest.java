package com.weather.forecast.service;

import com.weather.forecast.exception.WeatherNotFoundException;
import com.weather.forecast.model.Main;
import com.weather.forecast.model.OpenWeatherMapApiResponse;
import com.weather.forecast.model.WeatherResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 * test class for validating unit testing of  WeatherServiceImpl file
 */
@SpringBootTest
public class WeatherServiceImplUnitTest {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private CaffeineCache cache;

    @Autowired
    private WeatherServiceImpl weatherService;

    /**
     * validates happy flow with valid zip code
     */
    @Test
    public void testGetWeather() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setFromCache(false);
        weatherResponse.setTemprature(30.0);
        Mockito.when(cacheManager.getCache(Mockito.any())).thenReturn(cache);
        Mockito.when(cache.get(Mockito.any())).thenReturn(null);
        OpenWeatherMapApiResponse response = new OpenWeatherMapApiResponse();
        Main main = new Main();
        main.setFeels_like(1.0);
        main.setHumidity(20);
        main.setPressure(2l);
        main.setGrnd_level(3);
        main.setTemp_max(23.3);
        main.setSea_level(3l);
        response.setMain(main);
        Mockito.when(restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?zip=560040,IN&appid=57c07a051ca2f07748bedf2650d4763a&units=Metric", OpenWeatherMapApiResponse.class)).thenReturn(response);
        Assertions.assertEquals(weatherService.getWeather("560040").getTemp_max(), 23.3);
    }

    /**
     * validates not found exception weather forecast status for invalid zip code
     */
    @Test
    public void testNotFoundWeatherStatus() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setFromCache(false);
        weatherResponse.setTemprature(30.0);
        Mockito.when(cacheManager.getCache(Mockito.any())).thenReturn(cache);
        Mockito.when(cache.get(Mockito.any())).thenReturn(null);
        OpenWeatherMapApiResponse response = new OpenWeatherMapApiResponse();
        Main main = new Main();
        main.setFeels_like(1.0);
        main.setHumidity(20);
        main.setPressure(2l);
        main.setGrnd_level(3);
        main.setTemp_max(23.3);
        main.setSea_level(3l);
        response.setMain(main);
        Mockito.when(restTemplate
                .getForObject("https://api.openweathermap.org/data/2.5/weather?zip=560040123,IN&appid=57c07a051ca2f07748bedf2650d4763a&units=Metric", OpenWeatherMapApiResponse.class))
                .thenThrow(new WeatherNotFoundException(HttpStatus.NOT_FOUND, "weather not found"));
        Assertions.assertThrows(WeatherNotFoundException.class, ()-> weatherService.getWeather("560040123"));

    }

    /**
     * validates bad request exception weather forecast status for empty zip code
     */
    @Test
    public void testBadRequestWeatherStatus() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setFromCache(false);
        weatherResponse.setTemprature(30.0);
        Mockito.when(cacheManager.getCache(Mockito.any())).thenReturn(cache);
        Mockito.when(cache.get(Mockito.any())).thenReturn(null);
        OpenWeatherMapApiResponse response = new OpenWeatherMapApiResponse();
        Main main = new Main();
        main.setFeels_like(1.0);
        main.setHumidity(20);
        main.setPressure(2l);
        main.setGrnd_level(3);
        main.setTemp_max(23.3);
        main.setSea_level(3l);
        response.setMain(main);
        Mockito.when(restTemplate
                        .getForObject("https://api.openweathermap.org/data/2.5/weather?zip=,IN&appid=57c07a051ca2f07748bedf2650d4763a&units=Metric", OpenWeatherMapApiResponse.class))
                .thenThrow(new WeatherNotFoundException(HttpStatus.NOT_FOUND, "weather not found"));
        Assertions.assertThrows(WeatherNotFoundException.class, ()-> weatherService.getWeather(""));

    }
}
