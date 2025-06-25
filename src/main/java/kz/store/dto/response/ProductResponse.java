package kz.store.dto.response;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price, Long categoryId,
                              String categoryName) {
}
