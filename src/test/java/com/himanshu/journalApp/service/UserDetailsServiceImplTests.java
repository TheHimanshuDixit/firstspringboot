package com.himanshu.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.himanshu.journalApp.repository.UserRepository;

// @ActiveProfiles("dev")
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(com.himanshu.journalApp.entity.User.builder()
                        .username("Harsh")
                        .password("password")
                        .roles(List.of("USER"))
                        .build());
        UserDetails userDetails = userDetailsService.loadUserByUsername("Harsh");
        assertNotNull(userDetails);

    }
}
