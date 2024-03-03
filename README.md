# WheatherForcast
Backend API implementation to get weather forecast data which include current temperature and other extended information by taking zip code as an input
# API Name
http://{host}:8080/weather/current?zipCode={zipCode}
# Example
http://localhost:8080/weather/current?zipCode=581202
# Output Of This API
Displays weather forecast for the given zip code
1. Forecast should at least have current temperature
2. Additional points for high/low temperature and/or extended forecast
# Building of this Application
## Prerequisites
Make sure that following prerequisites are complated from end user side 
1. ### Maven 3.9.X
2. ### Java 17
3. ### Account Creation in openweathermap web site(Its optional for end users)
## Steps Execute Application Build
1. Clone the project using `git clone`
2. Go to the directory using cmd `cd WheatherForcast/weather-forecast`
3. Excute maven build cmd `mvn clean install`, As part of this build it runs unit tests and inetgrations tests and creates Uber excutable jar file.
4. Finally use java cmd `java -jar target/weather-forecast-1.0.0.jar` to start the Application after executing the comd user should be able to see the output like below.
   ![image](https://github.com/naveenkumargp/WheatherForcast/assets/10413446/a66b949b-ce74-44cb-8c34-5141b225587c)
# Validating Different Use cases 
To Validate multiple use case we are using curl command in this command we call the API with zipcode as parameter.
1. ### with valid zip code
    `curl 'http://localhost:8080/weather/current?zipCode=581202'` when user executes this cmd he will see following o/p
   ![image](https://github.com/naveenkumargp/WheatherForcast/assets/10413446/385248e7-d7ee-482b-a174-956897e9733d)
   when user executes same cmd with same input as above results will fetched from cache instead of calling actual remote API as below.
   ![image](https://github.com/naveenkumargp/WheatherForcast/assets/10413446/00b73113-3661-4518-a9fe-cde6de7779de)
2. ### with invalid zip code
   `curl 'http://localhost:8080/weather/current?zipCode=76` when user executes this cmd with invalid  he will see following o/p with 404 not found message
    ![image](https://github.com/naveenkumargp/WheatherForcast/assets/10413446/cb48eb79-71f2-4c5d-bd58-3979c6a2472b)
3. ### with empty zip code
    `curl 'http://localhost:8080/weather/current?zipCode=` when user executes this cmd with empty zip code he will see following o/p with 400 bad request message
   ![image](https://github.com/naveenkumargp/WheatherForcast/assets/10413446/1767869f-e228-46fc-9049-a3c56c012cac)
**NOTE** : In All above use cases we are using country code IN(India), user can change it to any country code to get corresponding country weather forecast data this can be enhanced by accepting one more parameter either as part of the end point or we can have separate property in the code to have this feature.
# References 
1. https://start.spring.io/
2. https://openweathermap.org/api
3. https://spring.io/guides/gs/spring-boot
