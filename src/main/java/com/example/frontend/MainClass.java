package com.example.frontend;



import com.example.frontend.config.CoreConfiguration;
import com.example.frontend.domain.dto.CurrentWeatherDto;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) throws SQLException {


        CoreConfiguration coreConfiguration = new CoreConfiguration();
        RestTemplate restTemplate = coreConfiguration.restTemplate();
        CurrentWeatherDto currentWeatherDto = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=London&appid=e1ef7e03260c95637a3eb85597f01383", CurrentWeatherDto.class);

        System.out.println(currentWeatherDto.getMainDto().getFeels_like());


    }


}
