package kz.store.Repository;


import kz.store.dto.response.ProductResponse;
import kz.store.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    @Query(value = """
        SELECT p.id as id,
               p.name as name, 
               p.description as description,
               p.price as price,
               p.category_id as categoryId,
               c.name as categoryName
        FROM products p 
        INNER JOIN categories c ON p.category_id = c.id 
        WHERE p.description LIKE CONCAT('%', :searchTerm, '%')
        ORDER BY p.name ASC
        """, nativeQuery = true)
    List<ProductResponse> findProductResponsesByDescription(@Param("searchTerm") String searchTerm);

    @Override
    @EntityGraph("product-with-category")
    Page<ProductEntity> findAll(@Nullable Specification<ProductEntity> spec, Pageable pageable);
}
