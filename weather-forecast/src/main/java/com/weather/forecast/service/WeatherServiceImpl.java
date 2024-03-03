package com.weather.forecast.service;

import com.weather.forecast.exception.WeatherNotFoundException;
import com.weather.forecast.model.Main;
import com.weather.forecast.model.OpenWeatherMapApiResponse;
import com.weather.forecast.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class to hadnle the logic constructing  the forecast data through cache and openweathermap website
 */
@Service
public class WeatherServiceImpl implements WeatherService{

    @Autowired
    CacheManager cacheManager;

    @Autowired
    RestTemplate restTemplate;

    @Value("${openweathermap.api.url}")
    private String openWeatherMapApiUrl;
    @Value("${openweathermap.api.key}")
    private String openWeatherMapApiKey;

    /**
     * getWeather it gets data from cache if zip code data avaiable otherwise calls getFromAPI method to get latest data
     * @param zipCode accepts zipcode of a place
     * @return WeatherResponse pojo contains forecast data such as current temp, temp_min, temp_max etc
     */
    @Override
    @CachePut(value = "weatherCache", key = "#zipCode")
    public WeatherResponse getWeather(String zipCode) {
        WeatherResponse weatherResponse;
       if(null!= cacheManager.getCache("weatherCache").get(zipCode)){
            weatherResponse = cacheManager.getCache("weatherCache").get(zipCode, WeatherResponse.class);
            weatherResponse.setFromCache(true);
            long thirtyMinutesBeforeMillis = System.currentTimeMillis() - (30*60*1000);
            long weatherTimeStamp = weatherResponse.getTimeStamp();
            if(weatherTimeStamp < thirtyMinutesBeforeMillis){
                weatherResponse = getFromAPI(zipCode);
            }
       }
       else{
           weatherResponse = getFromAPI(zipCode);
       }
        return weatherResponse;
    }

    /**
     * getFromAPI method gets latest data from openweathermap website
     * @param zipCode accepts zipcode of a place
     * @return WeatherResponse pojo contains forecast data such as current temp, temp_min, temp_max etc
     */
    private WeatherResponse getFromAPI(String zipCode) {
        String apiUrl = String.format("%s?zip=%s,IN&appid=%s&units=Metric", openWeatherMapApiUrl, zipCode, openWeatherMapApiKey);
        try{
            OpenWeatherMapApiResponse response = restTemplate.getForObject(apiUrl, OpenWeatherMapApiResponse.class);
            WeatherResponse weatherResponse = getWeatherResponseBean(response);
            return weatherResponse;
        }
        catch (Exception e) {
            if(e.getMessage().startsWith("400")){
                throw new WeatherNotFoundException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            else {
                throw new WeatherNotFoundException(HttpStatus.NOT_FOUND, e.getMessage());
            }

        }
    }

    /**
     * getWeatherResponseBean helper method to convert OpenWeatherMapApiResponse object type to WeatherResponse
     * @param response it contains response data from openweathermap
     * @return WeatherResponse pojo contains forecast data such as current temp, temp_min, temp_max etc
     */
    private WeatherResponse getWeatherResponseBean(OpenWeatherMapApiResponse response) {
        WeatherResponse weatherResponse = new WeatherResponse();
        Main main = response.getMain();
        weatherResponse.setFromCache(false);
        weatherResponse.setTimeStamp(System.currentTimeMillis());
        weatherResponse.setPressure(main.getPressure());
        weatherResponse.setHumidity(main.getHumidity());
        weatherResponse.setGrnd_level(main.getGrnd_level());
        weatherResponse.setFeels_like(main.getFeels_like());
        weatherResponse.setTemprature(main.getTemp());
        weatherResponse.setTemp_min(main.getTemp_min());
        weatherResponse.setTemp_max(main.getTemp_max());
        weatherResponse.setSea_level(main.getSea_level());
        return weatherResponse;
    }
}
