package com.example.OrderService.clients;

import com.example.OrderService.model.Restaurant;
import org.springframework.web.client.RestTemplate;

public class CatalogeServiceClient {

    private String apiUrl = "http://localhost:8082/restaurants/";

    private final RestTemplate restTemplate = new RestTemplate();


    public Restaurant getRestaurantById(Integer id) {

        String url = apiUrl + id;
        return restTemplate.getForObject(url, Restaurant.class);
    }
}
