package com.example.OrderService.model;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    private Integer ID;
    private String name;
    private String location;
    private List<MenuItems> menuItems;

}