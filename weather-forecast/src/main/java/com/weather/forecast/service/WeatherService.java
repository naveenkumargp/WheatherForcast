package com.weather.forecast.service;

import com.weather.forecast.model.WeatherResponse;

public interface WeatherService {
    public WeatherResponse getWeather(String zipCode);

}
