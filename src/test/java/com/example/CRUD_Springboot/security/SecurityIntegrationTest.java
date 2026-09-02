package com.example.CRUD_Springboot.security;

import com.example.CRUD_Springboot.entity.User;
import com.example.CRUD_Springboot.service.JwtService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;


    // ---------------------------------------------------------
    // 1. No JWT token → 401 Unauthorized
    // ---------------------------------------------------------

    @Test
    void requestWithoutToken_shouldReturn401() throws Exception {

        mockMvc.perform(
                        get("/api/v1/products")
                )
                .andExpect(status().isUnauthorized());
    }


    // ---------------------------------------------------------
    // 2. USER JWT → GET products → allowed
    // ---------------------------------------------------------

    @Test
    void userToken_shouldBeAllowedToGetProducts() throws Exception {

        User user = new User();
        user.setUsername("user");
        user.setRole("USER");

        String token = jwtService.generateAccessToken(user);

        mockMvc.perform(
                        get("/api/v1/products")
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        "Bearer " + token
                                )
                )
                .andExpect(status().isOk());
    }


    // ---------------------------------------------------------
    // 3. USER JWT → POST product → forbidden
    // ---------------------------------------------------------

    @Test
    void userToken_shouldNotBeAllowedToCreateProduct() throws Exception {

        User user = new User();
        user.setUsername("user");
        user.setRole("USER");

        String token = jwtService.generateAccessToken(user);

        mockMvc.perform(
                        post("/api/v1/products")
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        "Bearer " + token
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "productName": "Laptop"
                                }
                                """)
                )
                .andExpect(status().isForbidden());
    }


    // ---------------------------------------------------------
    // 4. ADMIN JWT → GET products → allowed
    // ---------------------------------------------------------

    @Test
    void adminToken_shouldBeAllowedToGetProducts() throws Exception {

        User admin = new User();
        admin.setUsername("admin");
        admin.setRole("ADMIN");

        String token = jwtService.generateAccessToken(admin);

        mockMvc.perform(
                        get("/api/v1/products")
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        "Bearer " + token
                                )
                )
                .andExpect(status().isOk());
    }


    // ---------------------------------------------------------
    // 5. ADMIN JWT → POST product → allowed
    // ---------------------------------------------------------

    @Test
    void adminToken_shouldBeAllowedToCreateProduct() throws Exception {

        User admin = new User();
        admin.setUsername("admin");
        admin.setRole("ADMIN");

        String token = jwtService.generateAccessToken(admin);

        mockMvc.perform(
                        post("/api/v1/products")
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        "Bearer " + token
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "productName": "Laptop"
                                }
                                """)
                )
                .andExpect(status().isOk());
    }


    // ---------------------------------------------------------
    // 6. USER JWT → DELETE product → forbidden
    // ---------------------------------------------------------

    @Test
    void userToken_shouldNotBeAllowedToDeleteProduct() throws Exception {

        User user = new User();
        user.setUsername("user");
        user.setRole("USER");

        String token = jwtService.generateAccessToken(user);

        mockMvc.perform(
                        delete("/api/v1/products/1")
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        "Bearer " + token
                                )
                )
                .andExpect(status().isForbidden());
    }
}