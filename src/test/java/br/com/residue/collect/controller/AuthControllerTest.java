package br.com.residue.collect.controller;

import br.com.residue.collect.domain.authuser.TokenDto;
import br.com.residue.collect.domain.user.User;
import br.com.residue.collect.domain.user.UserRepository;
import br.com.residue.collect.domain.user.UserRole;
import br.com.residue.collect.domain.user.UserService;
import br.com.residue.collect.infra.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private AuthenticationManager authenticationManager;// Mock do serviço de usuário


    @Test
    @DisplayName("Should register user and return http:201")
    void testRegisterUser() throws Exception {
        String userJson = """
                {
                    "nome": "Antonio Souza",
                    "email": "antonio@example.com",
                    "senha": "password123",
                    "role": "USER"
                }
                """;

        Mockito.when(userService.save(Mockito.any())).thenReturn(null);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should login with mock user and return http:200")
    void testLoginUser() throws Exception {
        String loginJson = """
                {
                    "email": "antonio@example.com",
                    "senha": "password123"
                }
                """;

        // Simulando um objeto UserDetails
        UserDetails mockUserDetails = new User(UUID.randomUUID(), "Antonio Souza", "antonio@example.com", "password123", UserRole.USER);

        // Simulando o comportamento do AuthenticationManager
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(mockUserDetails, null, mockUserDetails.getAuthorities()));


        Mockito.when(tokenService.generateToken(Mockito.any(User.class)))
                .thenReturn(new TokenDto(mockUserDetails.getUsername(), "dummy-token")); // Simula a geração do token

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Verifica se o status HTTP é 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("dummy-token"));


    }
}