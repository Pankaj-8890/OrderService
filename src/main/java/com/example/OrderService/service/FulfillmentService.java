package com.example.OrderService.service;

import FulfillmentService.*;
import com.example.OrderService.model.Order;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class FulfillmentService {


    //    @Value("${converter.grpc.service.host}")
    private static final String host = "localhost";

    //    @Value("${converter.grpc.service.port}")
    private static final int port = 9090;


    public static AssignOrderResponse assignOrder(Orders order, String pickupLocation) throws DeliveryExecutiveNotFoundException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(fulfillmentServiceHost, port)
                .usePlaintext().build();

        FulfillmentServiceGrpc.FulfillmentServiceBlockingStub stub = FulfillmentServiceGrpc.newBlockingStub(channel);
        Order orderToAssign = Order.newBuilder().setOrderId(order.getOrderId()).setStatus(order.getStatus().toString()).
                setTotalPrice(order.getTotal_price().floatValue()).setPickUpLocation(pickupLocation).setDropLocation(order.getCustomer().getAddress())
                .build();
        AssignOrderRequest request = AssignOrderRequest.newBuilder().setOrder(orderToAssign).build();
        try{
            var response = stub.assignOrder(request);
            channel.shutdown();
            return response;
        }catch(Exception exception) {
            throw new DeliveryExecutiveNotFoundException("No delivery executive found at location " + pickupLocation);
        }
    }


}

