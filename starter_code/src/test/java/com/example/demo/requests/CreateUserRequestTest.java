package com.example.demo.requests;

import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserRequestTest {

    @Tag("sanity")
    @Tag("regression")
    @Test
    void testGettersAndSetters() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("john");
        request.setPassword("pass1234");
        request.setConfirmPassword("pass1234");

        assertEquals("john", request.getUsername());
        assertEquals("pass1234", request.getPassword());
        assertEquals("pass1234", request.getConfirmPassword());
    }
}
