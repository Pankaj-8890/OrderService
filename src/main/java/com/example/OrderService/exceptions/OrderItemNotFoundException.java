package com.example.OrderService.exceptions;

public class OrderItemNotFoundException extends Exception{

    public OrderItemNotFoundException(String str){
        super(str);
    }

}
