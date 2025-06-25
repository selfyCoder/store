package kz.store.service;

import kz.store.Repository.ActionRepository;
import kz.store.dto.response.ProductAndActionResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ActionServiceTest {
    @Test
    void returnsActiveActions() {
        // given
        ActionRepository repo = mock(ActionRepository.class);
        ActionService service = new ActionService(repo);

        ProductAndActionResponse dto = new ProductAndActionResponse(
                1L, "Laptop", BigDecimal.valueOf(1200.0), "Back to School",
                Instant.now(), Instant.now().plusSeconds(86400)
        );
        when(repo.findProductsWithActiveActions()).thenReturn(List.of(dto));

        List<ProductAndActionResponse> result = service.getProductsInActiveActions();

        assertThat(result).hasSize(1);
        verify(repo, times(1)).findProductsWithActiveActions();
    }
}
