package com.example.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Integer order_id;
    private String user_name;
    private List<OrderItems> orderItems;
    private Double totalPrice;
    private OrderStatus orderStatus;

}
