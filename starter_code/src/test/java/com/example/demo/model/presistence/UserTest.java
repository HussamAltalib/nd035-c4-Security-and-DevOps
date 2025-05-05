package com.example.demo.model.presistence;


import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setPassword("password123");

        Cart cart = new Cart();
        user.setCart(cart);

        assertEquals(1L, user.getId());
        assertEquals("john", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals(cart, user.getCart());
    }
}
