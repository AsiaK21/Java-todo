package com.asia.asia;

import com.asia.asia.entities.Role;
import com.asia.asia.entities.User;
import com.asia.asia.services.AuthenticationService;
import com.asia.asia.services.impl.JwtServiceImpl;
import com.asia.asia.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void testAssignAdminRole() throws Exception {
        // Tworzenie użytkownika z rolą admina (tutaj można dostosować do swoich potrzeb)
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("adminPassword"); // Pamiętaj o poprawnym zaszyfrowaniu hasła
        adminUser.setRole(Role.ADMIN);

        // Zapisanie użytkownika w bazie danych
        authenticationService.save(adminUser);

        // Symulacja logowania
        Authentication auth = createAuthentication("admin", Collections.singletonList(() -> "ROLE_ADMIN"));
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Testowanie endpointu przypisania roli admina
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/assign-role")
                        .param("username", "someUser")) // Dostosuj do rzeczywistych danych
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        // Przywracanie oryginalnego stanu kontekstu bezpieczeństwa po zakończeniu testu
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private Authentication createAuthentication(String username, Collection<? extends GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(username, "password", authorities);
    }
}
