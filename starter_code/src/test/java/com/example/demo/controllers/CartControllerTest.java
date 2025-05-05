package com.example.demo.controllers;

import com.example.demo.model.persistence.*;
import com.example.demo.model.persistence.repositories.*;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    private User user;
    private Item item;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("john");
        Cart cart = new Cart();
        cart.setTotal(BigDecimal.ZERO);
        user.setCart(cart);

        item = new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.TEN);
    }

    @Test
    void testAddToCart_happyPath() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("john");
        request.setItemId(1L);
        request.setQuantity(1);

        when(userRepository.findByUsername("john")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getItems().size());
    }

    @Test
    void testAddToCart_userNotFound() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("unknown");

        when(userRepository.findByUsername("unknown")).thenReturn(null);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testAddToCart_itemNotFound() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("john");
        request.setItemId(2L);

        when(userRepository.findByUsername("john")).thenReturn(user);
        when(itemRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testRemoveFromCart_userNotFound() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("unknown");

        when(userRepository.findByUsername("unknown")).thenReturn(null);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testRemoveFromCart_itemNotFound() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("john");
        request.setItemId(999L);

        when(userRepository.findByUsername("john")).thenReturn(user);
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertEquals(404, response.getStatusCodeValue());
    }

}

