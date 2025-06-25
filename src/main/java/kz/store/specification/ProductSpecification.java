package kz.store.specification;

import jakarta.persistence.criteria.Join;
import kz.store.dto.filter.ProductFilter;
import kz.store.entity.CategoryEntity;
import kz.store.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {


    public static Specification<ProductEntity> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction(); // всегда true
            }

            Join<ProductEntity, CategoryEntity> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
        };
    }


    public static Specification<ProductEntity> hasPriceGreaterThanOrEqual(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }


    public static Specification<ProductEntity> hasPriceLessThanOrEqual(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (maxPrice == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }


    public static Specification<ProductEntity> hasDescriptionContaining(String description) {
        return (root, query, criteriaBuilder) -> {
            if (description == null || description.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String searchTerm = "%" + description.trim().toLowerCase() + "%";
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")),
                    searchTerm
            );
        };
    }

    public static Specification<ProductEntity> withFilters(ProductFilter filter) {
        return hasCategoryId(filter.categoryId())
                .and(hasPriceGreaterThanOrEqual(filter.minPrice()))
                .and(hasPriceLessThanOrEqual(filter.maxPrice()))
                .and(hasDescriptionContaining(filter.description()));
    }
}
