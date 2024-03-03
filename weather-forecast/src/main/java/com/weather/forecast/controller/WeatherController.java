package com.weather.forecast.controller;

import com.weather.forecast.exception.WeatherNotFoundException;
import com.weather.forecast.model.WeatherResponse;
import com.weather.forecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * controller class to handle incoming request for to get the weather status
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

   @Autowired
   WeatherService weatherService;

   /**
    * getCurrentWeather : accpets zipcode as parameter send the weather forecast data
    * @param zipCode
    * @return  WeatherResponse contains forecast data such as current temp, temp_min, temp_max etc
    */
   @GetMapping("/current")
   public ResponseEntity<WeatherResponse> getCurrentWeather(@RequestParam String zipCode){
      WeatherResponse weatherResponse;
      try{
         weatherResponse = weatherService.getWeather(zipCode);
      }
      catch (WeatherNotFoundException exception){
         throw new ResponseStatusException(exception.getReturnCode(), exception.getMessage());
      }
      return ResponseEntity.ok(weatherResponse);
   }

}
