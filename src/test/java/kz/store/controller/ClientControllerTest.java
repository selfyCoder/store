package kz.store.controller;

import kz.store.dto.response.ClientResponse;
import kz.store.entity.ClientEntity;
import kz.store.mapper.ClientMapper;
import kz.store.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
@WebMvcTest(ClientController.class)
@Import(ClientControllerTest.TestConfig.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @Test
    void search_ReturnsClient_WhenIdIsFound() throws Exception {
        ClientEntity client = new ClientEntity(1L, "John", "Doe", "Middle", "123456789012");
        ClientResponse response = new ClientResponse(1L, "John", "Doe", "Middle", "123456789012");

        Mockito.when(clientService.findByFields(1L, null)).thenReturn(Optional.of(client));
        Mockito.when(clientMapper.toResponse(client)).thenReturn(response);

        mockMvc.perform(get("/clients/search").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        ClientService clientService() {
            return Mockito.mock(ClientService.class);
        }

        @Bean
        ClientMapper clientMapper() {
            return Mockito.mock(ClientMapper.class);
        }
    }
}
