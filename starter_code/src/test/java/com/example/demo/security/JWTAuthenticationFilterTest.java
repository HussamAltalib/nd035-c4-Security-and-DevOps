package com.example.demo.security;

import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;

class JWTAuthenticationFilterTest {

    private JWTAuthenticationFilter filter;

    @Mock
    private AuthenticationManager authManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new JWTAuthenticationFilter(authManager);
    }

    @Tag("sanity")
    @Tag("regression")
    @Test
    void testAttemptAuthentication() throws Exception {
        User user = new User();
        user.setUsername("john");
        user.setPassword("password");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(new ObjectMapper().writeValueAsBytes(user));
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        filter.attemptAuthentication(request, response);
        verify(authManager, times(1)).authenticate(any());
    }


}
