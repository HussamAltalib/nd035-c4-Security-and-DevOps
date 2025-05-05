package com.example.demo.controllers;

import com.example.demo.model.persistence.*;
import com.example.demo.model.persistence.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("john");
        user.setCart(new Cart());
    }

    @Test
    void testSubmitOrder_happyPath() {
        when(userRepository.findByUsername("john")).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit("john");

        assertEquals(200, response.getStatusCodeValue());
        verify(orderRepository, times(1)).save(any(UserOrder.class));
    }

    @Test
    void testSubmitOrder_userNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        ResponseEntity<UserOrder> response = orderController.submit("unknown");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetOrdersForUser_happyPath() {
        when(userRepository.findByUsername("john")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(List.of(new UserOrder()));

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("john");

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetOrdersForUser_userNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("unknown");

        assertEquals(404, response.getStatusCodeValue());
    }


}
