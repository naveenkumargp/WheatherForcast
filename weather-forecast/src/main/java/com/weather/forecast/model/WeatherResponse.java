package com.weather.forecast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private Double temprature;
    private boolean fromCache;
    private long timeStamp;
    private Double feels_like;
    private Double temp_min;
    private Double temp_max;
    private Long pressure;
    private int humidity;
    private long sea_level;

    private long grnd_level;
}
