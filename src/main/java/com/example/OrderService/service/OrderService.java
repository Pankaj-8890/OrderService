package com.example.OrderService.service;

import com.example.OrderService.clients.CatalogeServiceClient;
import com.example.OrderService.exceptions.OrderItemNotFoundException;
import com.example.OrderService.model.*;
import com.example.OrderService.model.MenuItems;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public OrderResponse createOrder(String username, OrderRequest orderRequest) throws OrderItemNotFoundException {
        User usersModel = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        Restaurant restaurant = new CatalogeServiceClient().getRestaurantById(orderRequest.getRestaurant_id());


        Map<String, MenuItems> menuMap = new HashMap<>();
        for (MenuItems menuItems : restaurant.getMenuItems()) {
            menuMap.put(menuItems.getName(), menuItems);
        }

        List<OrderItems> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (OrderItems orderItem : orderRequest.getOrderItems()) {
            String ItemName = orderItem.getName();
            if (menuMap.containsKey(ItemName)) {
                MenuItems menuItems = menuMap.get(ItemName);
                totalPrice += menuItems.getPrice() * orderItem.getQuantity();

                orderItems.add(orderItem);
            }
            else{
                throw new OrderItemNotFoundException("OrderItem not found " + ItemName);
            }
        }
        Orders order = new Orders(usersModel,totalPrice,OrderStatus.CREATED,orderItems,orderRequest.getLocation());
        Orders returnedOrder = orderRepository.save(order);

        return new OrderResponse(returnedOrder.getId(),username,orderItems,totalPrice,OrderStatus.CREATED);
    }

    public Orders getOrder(Integer id){
        return orderRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
