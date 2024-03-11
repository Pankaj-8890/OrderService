package com.example.OrderService.service;

import FulfillmentService.*;
import com.example.OrderService.exceptions.DeliveryExecutiveNotAvailableException;
import com.example.OrderService.model.Orders;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class FulfillmentService {


    //    @Value("${converter.grpc.service.host}")
    private static final String host = "localhost";

    //    @Value("${converter.grpc.service.port}")
    private static final int port = 9090;


    public static AssignedOrderResponse assignOrder(Orders order) throws DeliveryExecutiveNotAvailableException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext().build();

        fulfillmentGrpc.fulfillmentBlockingStub stub = fulfillmentGrpc.newBlockingStub(channel);
        Order orderToAssign = Order.newBuilder().setId(order.getId()).setOrderStatus(order.getOrderStatus().toString()).setLocation(order.getLocation())
                .build();

        AssignedOrderRequest request = AssignedOrderRequest.newBuilder().setOrder(orderToAssign).build();
        try{
            var response = stub.assignedOrder(request);
            channel.shutdown();
            return response;
        }catch(Exception exception) {
            throw new DeliveryExecutiveNotAvailableException("No delivery executive found at location");
        }
    }


}

