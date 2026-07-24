package davisantos.dev.OrderManager.modules.order.dto;

import davisantos.dev.OrderManager.modules.product.dto.ProductResponse;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        ProductResponse product,
        BigDecimal price,
        int quantity
) {
}
