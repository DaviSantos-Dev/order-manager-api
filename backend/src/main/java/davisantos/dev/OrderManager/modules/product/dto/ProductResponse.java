package davisantos.dev.OrderManager.modules.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        int quantity,
        String status,
        boolean active
) {
}
