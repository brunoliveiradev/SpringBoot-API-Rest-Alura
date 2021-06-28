package com.alura.forum.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("prod")
class AuthenticationControllerTest {

    //Para testar um controller não injetamos as classes, mas sim o mockMvc.
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Retorna 400 - Bad Request quando dados de autenticação forem inválidos")
    void deveriaDevolverBadRequest() throws Exception {
        URI uri = new URI("/auth");
        String json = "{\"email\": \"invalido@email.com\"," +
                        "\"senha\": \"123456\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    @DisplayName("Retorna 200 - Ok quando Credenciais válidas")
    public void okAoReceberCredenciaisValidas() throws Exception {
        URI uri = new URI("/auth");
        String body = "{\"email\":\"aluno@email.com\",\"senha\":\"123456\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//        String resposta = result.getResponse().getContentAsString();
    }
}