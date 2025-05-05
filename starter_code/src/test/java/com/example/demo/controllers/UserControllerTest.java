package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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

    @Tag("sanity")
    @Tag("regression")
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

    @Tag("negative")
    @Test
    void testCreateUser_passwordTooShort() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setPassword("short");
        request.setConfirmPassword("short");

        ResponseEntity<User> response = userController.createUser(request);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Tag("negative")
    @Test
    void testCreateUser_passwordsDoNotMatch() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setConfirmPassword("wrongpass");

        ResponseEntity<User> response = userController.createUser(request);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Tag("negative")
    @Test
    void testFindById_userNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.findById(999L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Tag("sanity")
    @Test
    void testFindById_userFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("testuser", response.getBody().getUsername());
    }

    @Tag("sanity")
    @Test
    void testFindByUserName_userFound() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        ResponseEntity<User> response = userController.findByUserName("testuser");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("testuser", response.getBody().getUsername());
    }

    @Tag("negative")
    @Test
    void testFindByUserName_userNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        ResponseEntity<User> response = userController.findByUserName("unknown");
        assertEquals(404, response.getStatusCodeValue());
    }

}
