package kz.store.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductAndActionResponse(Long productId,
                                       String productName,
                                       BigDecimal price,
                                       String actionName,
                                       Instant beginDate,
                                       Instant endDate) {
}
