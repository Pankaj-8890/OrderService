package com.example.OrderService.controllers;

import com.example.OrderService.exceptions.UserAlreadyExistsException;
import com.example.OrderService.model.User;
import com.example.OrderService.service.OrderService;
import com.example.OrderService.service.UserService;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setup(){
        reset(userService);
    }



    @Test
    void TestUserCreatedSuccessfully() throws Exception {
        User userRequest = new User("test","test","test");
        User userResponse = new User("test","test","test");

        when(userService.createUser("test","test","test")).thenReturn(userResponse);
        mockMvc.perform(post("/users").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(userRequest))).andExpect(status().isOk()).
                andExpect(jsonPath("$.username").value("test"));
    }



}
