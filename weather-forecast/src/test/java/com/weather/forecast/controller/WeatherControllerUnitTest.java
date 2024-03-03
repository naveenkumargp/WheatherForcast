package com.weather.forecast.controller;

import com.weather.forecast.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class file to validate unit testing of the WeatherController class
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    WeatherService weatherService;

    /**
     * validates /weather/current?zipCode url for successfull scenario
     * @throws Exception
     */
    @Test
    public void getWeatherStatus() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/current?zipCode=560040")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(weatherService, Mockito.times(1)).getWeather("560040");
    }

}
