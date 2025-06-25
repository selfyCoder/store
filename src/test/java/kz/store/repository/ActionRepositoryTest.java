package kz.store.repository;

import kz.store.Repository.ActionRepository;
import kz.store.dto.response.ProductAndActionResponse;
import kz.store.entity.ActionEntity;
import kz.store.entity.CategoryEntity;
import kz.store.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ActionRepositoryTest {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("Should return products with active actions")
    void findProductsWithActiveActions_shouldReturnData() {
        CategoryEntity category = new CategoryEntity(null, "Electronics", Collections.emptyList());
        category = entityManager.persist(category);
        // создаём продукт
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setCategory(category);
        entityManager.persist(product);

        // создаём акцию, которая активна сейчас
        ActionEntity action = new ActionEntity();
        action.setName("Test Action");
        action.setBeginDate(Instant.now().minusSeconds(3600));
        action.setEndDate(Instant.now().plusSeconds(86400));
        action.setProduct(product);
        entityManager.persist(action);

        entityManager.flush();
        entityManager.clear();

        List<ProductAndActionResponse> result = actionRepository.findProductsWithActiveActions();

        assertThat(result).hasSize(1);
        ProductAndActionResponse response = result.getFirst();
        assertThat(response.productName()).isEqualTo("Test Product");
        assertThat(response.actionName()).isEqualTo("Test Action");
    }

    @Test
    @DisplayName("Should return empty list if no active actions")
    void findProductsWithActiveActions_shouldReturnEmptyList() {

        List<ProductAndActionResponse> result = actionRepository.findProductsWithActiveActions();

        assertThat(result).isEmpty();
    }
}
