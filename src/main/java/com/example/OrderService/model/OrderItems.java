package com.example.OrderService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Join;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderItems {

    private String name;
    private Integer quantity;

}


