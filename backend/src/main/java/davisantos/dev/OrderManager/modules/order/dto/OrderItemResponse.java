package davisantos.dev.OrderManager.modules.order.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        ProductResponse product,
        BigDecimal price,
        int quantity
) {
}
