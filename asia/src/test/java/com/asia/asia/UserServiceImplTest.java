package com.asia.asia;

import com.asia.asia.entities.User;
import com.asia.asia.repositories.UserRepository;
import com.asia.asia.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.asia.asia.entities.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        String username = "testAsia";
        User testUser = new User(1L, "Joanna", "Kozar", username, "asia@onet.pl", "password", USER);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        String username = "nieStworzonyUżytkownik";
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        assertThrows(UsernameNotFoundException.class,
                () -> userService.userDetailsService().loadUserByUsername(username));
    }

    @Test
    void getUserById_UserExists_ReturnsOptionalUser() {
        Long userId = 1L;
        User testUser = new User(userId, "Joanna", "Kozar", "joanna", "asia@onet.pl", "password", USER);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        Optional<User> result = userService.getUserById(userId);
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
    }

    @Test
    void getUserById_UserNotFound_ReturnsEmptyOptional() {
        Long nonExistingUserId = 999L;
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        Optional<User> result = userService.getUserById(nonExistingUserId);
        assertTrue(result.isEmpty());
    }
}
