package com.example.OrderService.controllers;


import com.example.OrderService.exceptions.OrderItemNotFoundException;
import com.example.OrderService.model.*;
import com.example.OrderService.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;
    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setup(){
        reset(orderService);
    }

    @Test
    @WithMockUser(username = "testUser")
    void TestOrderCreatedForValidUser() throws Exception {

        List<OrderItems> orderItemsList = new ArrayList<>(List.of(new OrderItems("rice",2)));
        OrderRequest orderRequest = new OrderRequest(1,orderItemsList,"test");
        OrderResponse orderResponse = new OrderResponse(1,"testUser",orderItemsList,200.0, OrderStatus.CREATED);
        when(orderService.createOrder("testUser",orderRequest)).thenReturn(orderResponse);

        mockMvc.perform(post("/orders").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(orderRequest))).andExpect(status().isOk()).
                andExpect(jsonPath("$.orderItems[0]").exists()).
                andExpect(jsonPath("$.orderStatus").value("CREATED"));

    }

    @Test
    @WithMockUser(username = "testUser")
    void testFetchOrder_ExpectSuccessful() throws Exception {

        List<OrderItems> orderItemsList = new ArrayList<>(List.of(new OrderItems("samosa",1)));
        OrderRequest orderRequest = new OrderRequest(1,orderItemsList,"test");
        User user = new User("testUser","test","INDIA");
        Orders orderResponse = new Orders(user,120.5,OrderStatus.CREATED,orderItemsList,"INDIA");

        when(orderService.getOrder( 1)).thenReturn(orderResponse);

        mockMvc.perform(get("/orders/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(orderRequest))).
                andExpect(status().isOk());
        verify(orderService, times(1)).getOrder( 1);
    }



}
