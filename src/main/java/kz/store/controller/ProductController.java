package kz.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.store.Repository.ClientRepository;
import kz.store.Repository.ProductRepository;
import kz.store.dto.PageDto;
import kz.store.dto.filter.ProductFilter;
import kz.store.dto.response.ProductResponse;
import kz.store.entity.ProductEntity;
import kz.store.mapper.ProductMapper;
import kz.store.specification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Operation(summary = "получение информации о товаре по description, метод должен использовать нативный sql")
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProductsByDescription(
            @RequestParam String description) {

        if (description == null || description.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<ProductResponse> products = productRepository
                .findProductResponsesByDescription(description.trim());

        return ResponseEntity.ok(products);
    }

    @Operation(summary = """
            получение информации о товаре по фильтру (spring data specification jpa),
             в фильтре использовать поля category_id, price, description
            """)
    @GetMapping("/filter")
    public ResponseEntity<PageDto<ProductResponse>> filterProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        ProductFilter filter = new ProductFilter(categoryId, minPrice, maxPrice, description);

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<ProductEntity> spec = ProductSpecification.withFilters(filter);
        Page<ProductEntity> products = productRepository.findAll(spec, pageable);

        return ResponseEntity.ok(PageDto.from(products.map(productMapper::toResponse)));
    }
}
