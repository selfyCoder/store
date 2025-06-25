package kz.store.dto.filter;

import java.math.BigDecimal;

public record ProductFilter(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String description) {
}
