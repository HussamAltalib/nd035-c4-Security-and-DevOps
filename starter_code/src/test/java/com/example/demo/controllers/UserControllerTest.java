package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_happyPath() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setConfirmPassword("password123");

        when(bCryptPasswordEncoder.encode("password123")).thenReturn("hashed");

        ResponseEntity<User> response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("testuser", response.getBody().getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_passwordTooShort() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setPassword("short");
        request.setConfirmPassword("short");

        ResponseEntity<User> response = userController.createUser(request);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testCreateUser_passwordsDoNotMatch() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setConfirmPassword("wrongpass");

        ResponseEntity<User> response = userController.createUser(request);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testFindById_userNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.findById(999L);
        assertEquals(404, response.getStatusCodeValue());
    }

}
