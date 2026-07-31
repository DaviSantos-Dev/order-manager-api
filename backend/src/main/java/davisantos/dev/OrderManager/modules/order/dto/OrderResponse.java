package davisantos.dev.OrderManager.modules.order.dto;

import davisantos.dev.OrderManager.modules.user.domain.User;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long id,
        User client,
        List<OrderItemResponse> orderItems,
        String status,
        BigDecimal total
) {
}
