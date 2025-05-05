package com.example.demo.model.presistence;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class UserOrderTest {

    @Test
    void testCreateFromCart() {
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        cart.setTotal(BigDecimal.TEN);
        User user = new User();
        cart.setUser(user);

        UserOrder order = UserOrder.createFromCart(cart);
        assertEquals(cart.getTotal(), order.getTotal());
        assertEquals(cart.getUser(), order.getUser());
        assertEquals(cart.getItems(), order.getItems());
    }
}
