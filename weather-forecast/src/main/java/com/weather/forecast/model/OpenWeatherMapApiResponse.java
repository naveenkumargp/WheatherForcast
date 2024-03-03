package com.weather.forecast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties
@Data
public class OpenWeatherMapApiResponse {
    private Main main;
}
