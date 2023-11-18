package com.asia.asia;

import com.asia.asia.entities.Role;
import com.asia.asia.entities.User;
import com.asia.asia.services.impl.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@SpringBootTest
class JwtServiceImplTest {

    @Mock
    private UserDetails userDetails;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void extractLoggedInUserId() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setRole(Role.USER);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        Long loggedInUserId = jwtService.extractLoggedInUserId();

        assertEquals(1L, loggedInUserId);
    }

    @Test
    void extractLoggedInUserRole() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setRole(Role.USER);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        Role loggedInUserRole = jwtService.extractLoggedInUserRole();

        assertEquals(Role.USER, loggedInUserRole);
    }

    @Test
    void getLoggedInUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setRole(Role.USER);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        User loggedInUser = jwtService.getLoggedInUser();

        assertEquals(user, loggedInUser);
    }

    private User createUserWithIdAndRole(Long id, Role role) {
        User user = new User();
        user.setId(id);
        user.setUsername("testUser");
        user.setRole(role);
        return user;

    }
}
