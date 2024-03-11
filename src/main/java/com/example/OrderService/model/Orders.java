package com.example.OrderService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    private Double totalPrice;

    private String location;

    @ElementCollection
    private List<OrderItems> orderItems;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    public Orders(User usersModel, double totalPrice, OrderStatus orderStatus, List<OrderItems> orderItems,String location) {
        this.user = usersModel;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
        this.location = location;
    }

}