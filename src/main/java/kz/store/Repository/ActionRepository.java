package kz.store.Repository;

import kz.store.dto.response.ProductAndActionResponse;
import kz.store.entity.ActionEntity;
import kz.store.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
    @Query("""
                SELECT new kz.store.dto.response.ProductAndActionResponse(
                    p.id,
                    p.name,
                    p.price,
                    a.name,
                    a.beginDate,
                    a.endDate
                )
                FROM ActionEntity a
                JOIN a.product p
                WHERE a.beginDate <= CURRENT_TIMESTAMP AND a.endDate >= CURRENT_TIMESTAMP
            """)
    List<ProductAndActionResponse> findProductsWithActiveActions();

}
