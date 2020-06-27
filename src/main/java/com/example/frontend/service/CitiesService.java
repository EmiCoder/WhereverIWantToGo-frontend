package com.example.frontend.service;

import com.example.frontend.config.CoreConfiguration;
import com.example.frontend.database.DbManager;
import com.example.frontend.domain.ResponseCity;
import com.example.frontend.domain.UserRequest;
import com.example.frontend.domain.dto.CurrentWeatherDto;
import lombok.Getter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Getter
public class CitiesService {

    private final int TEMPERATURE_SCOPE = 2;
    private Statement statement;
    private ResultSet rs;

    CoreConfiguration coreConfiguration = new CoreConfiguration();
    RestTemplate restTemplate = coreConfiguration.restTemplate();

    private static final double TEMPERATURE_INDICATOR =  273.15;

    public CitiesService()  {
    }

    public List<ResponseCity> getCitiesToGo(UserRequest request) throws SQLException {
        ResultSet resultSet = getStatement().executeQuery("select COUNTRY_CODE from countries where COUNTRY_NAME = \'" + request.getCountryName() + "\'");
        resultSet.next();
        String countryCode = resultSet.getString(1);
        statement = getStatement();
        rs = statement.executeQuery("select CITY_ID, CITY_NAME, COUNTRY_CODE from cities_artificial_data where " +
                request.getMonth() + " > " + (Integer.parseInt(request.getTemperature()) - TEMPERATURE_SCOPE) + " and " + request.getMonth() +
                " < " + (request.getTemperature() + TEMPERATURE_SCOPE) + " and COUNTRY_CODE = \'" + countryCode + "\'");

        List<ResponseCity> list = new ArrayList<>();
        while (rs.next()) {
            Statement newStatement = getStatement();
            ResultSet countryName = newStatement.executeQuery("select COUNTRY_NAME from countries where COUNTRY_CODE = \'" + countryCode + "\'");
            countryName.next();
            ResponseCity city = new ResponseCity(
                    rs.getString(2),
                    countryName.getString(1),
                    getCurrentWeather(rs.getString(2)));
            list.add(city);
        }
        statement.close();
        return list;
    }

    private String getCurrentWeather(String city) {
        try {
            CurrentWeatherDto currentWeatherDto = restTemplate.getForObject(
                                                            "http://api.openweathermap.org/data/2.5/weather?q=" +
                                                                 city + "&appid=e1ef7e03260c95637a3eb85597f01383",
                                                                 CurrentWeatherDto.class);

            changeKelvinToCelcius(currentWeatherDto);
            return getCurrentWeatherString(currentWeatherDto);
        } catch (HttpClientErrorException e) {
            e.getStackTrace();
        } return "No current data";
    }

    private void changeKelvinToCelcius(CurrentWeatherDto currentWeatherDto) {
        currentWeatherDto.getMainDto().setFeels_like((int)(currentWeatherDto.getMainDto().getFeels_like() - TEMPERATURE_INDICATOR));
    }

    private String getCurrentWeatherString(CurrentWeatherDto currentWeatherDto) {
        return "T-" + String.valueOf(currentWeatherDto.getMainDto().getFeels_like()).substring(0,3) +
                "°C P-" + currentWeatherDto.getMainDto().getPressure() +
                "hPa H-" + currentWeatherDto.getMainDto().getHumidity() + "%";
    }

    private Statement getStatement() throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        return dbManager.getConnection().createStatement();
    }

}
