package com.example.OrderService.model;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Integer restaurant_id;
    private List<OrderItems> orderItems;

}
