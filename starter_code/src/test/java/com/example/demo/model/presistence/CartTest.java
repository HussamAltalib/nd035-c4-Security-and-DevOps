package com.example.demo.model.presistence;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Tag("sanity")
    @Tag("regression")
    @Test
    void testAddAndRemoveItem() {
        Cart cart = new Cart();
        Item item = new Item();
        item.setPrice(BigDecimal.TEN);
        cart.setItems(new ArrayList<>());
        cart.setTotal(BigDecimal.ZERO);

        cart.addItem(item);
        assertEquals(1, cart.getItems().size());
        assertEquals(BigDecimal.TEN, cart.getTotal());

        cart.removeItem(item);
        assertEquals(0, cart.getItems().size());
        assertEquals(BigDecimal.ZERO, cart.getTotal());
    }
}
