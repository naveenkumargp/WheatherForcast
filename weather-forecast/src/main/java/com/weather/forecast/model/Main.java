package com.weather.forecast.model;

import lombok.Data;

@Data
public class Main {
    private  Double temp;
    private Double feels_like;
    private Double temp_min;
    private Double temp_max;
    private Long pressure;
    private int humidity;
    private long sea_level;
    private long grnd_level;
}
