package com.example.demo.requests;


import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModifyCartRequestTest {

    @Test
    void testGettersAndSetters() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("john");
        request.setItemId(1L);
        request.setQuantity(2);

        assertEquals("john", request.getUsername());
        assertEquals(1L, request.getItemId());
        assertEquals(2, request.getQuantity());
    }
}
