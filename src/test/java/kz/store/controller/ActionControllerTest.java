package kz.store.controller;

import kz.store.dto.response.ProductAndActionResponse;
import kz.store.service.ActionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActionController.class)
class ActionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActionService actionService;

    @Test
    void testSomething() throws Exception {
        Mockito.when(actionService.getProductsInActiveActions())
                .thenReturn(List.of(new ProductAndActionResponse(
                        1L, "Laptop", BigDecimal.valueOf(1200.0), "Back to School",
                        Instant.now(), Instant.now().plusSeconds(86400)
                )));

        mockMvc.perform(get("/actions/active/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ActionService actionService() {
            return Mockito.mock(ActionService.class);
        }
    }
}

