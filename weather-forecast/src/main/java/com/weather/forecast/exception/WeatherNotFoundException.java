package com.weather.forecast.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class WeatherNotFoundException extends RuntimeException{

    private   HttpStatus returnCode;
    public WeatherNotFoundException(HttpStatus httpStatusCode, String exception){
        super(exception);
        this.returnCode = httpStatusCode;
    }
}
