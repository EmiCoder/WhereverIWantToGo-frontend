package com.example.frontend.service;

import com.example.frontend.domain.ResponseCity;
import com.example.frontend.domain.UserRequest;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseCityService {

    private List<ResponseCity> responseCities;
    private static ResponseCityService responseCityService;

    private ResponseCityService() {
//        this.responseCities = exampleData();
    }

    public static ResponseCityService getInstance() {
        if (responseCityService == null) {
            responseCityService = new ResponseCityService();
        }
        return responseCityService;
    }

    public List<ResponseCity> getResponseCities() {
        return new ArrayList<>(responseCities);
    }

//    private List<ResponseCity> exampleData() {
//        List<ResponseCity> cities = new ArrayList<>();
//        cities.add(new ResponseCity("Radom", "Polska", "GoogleMaps"));
//        cities.add(new ResponseCity("Warszawa", "Polska", "GoogleMaps"));
//        cities.add(new ResponseCity("Kraków", "Polska", "GoogleMaps"));
//        cities.add(new ResponseCity("Berlin", "Niemcy", "GoogleMaps"));
//        cities.add(new ResponseCity("Monachium", "Niemcy", "GoogleMaps"));
//        cities.add(new ResponseCity("Paryz", "Francja", "GoogleMaps"));
//        cities.add(new ResponseCity("Chicago", "USA", "GoogleMaps"));
//        cities.add(new ResponseCity("Michigan", "USA", "GoogleMaps"));
//        return cities;
//    }

    public List<ResponseCity> getCities(UserRequest userRequest) {
        return responseCities.stream()
                               .filter(u -> u.getCountryName().equals(userRequest.getCountryName()))
                               .collect(Collectors.toList());
    }
}
