package com.example.OrderService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItems {

    private Integer ID;
    private String name;
    private double price;
    private Integer restaurantsID;

}