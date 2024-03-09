package com.example.OrderService.exceptions;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String str){
        super(str);
    }

}