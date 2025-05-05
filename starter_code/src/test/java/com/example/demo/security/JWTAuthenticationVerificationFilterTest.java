package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class JWTAuthenticationVerificationFilterTest {

    private JWTAuthenticationVerficationFilter filter;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new JWTAuthenticationVerficationFilter(authManager);
    }

    @Test
    void testDoFilterInternal_withValidToken() throws Exception {
        String token = "Bearer " + com.auth0.jwt.JWT.create()
                .withSubject("john")
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

        when(request.getHeader(SecurityConstants.HEADER_STRING)).thenReturn(token);

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_withNoToken() throws Exception {
        when(request.getHeader(SecurityConstants.HEADER_STRING)).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }

}
