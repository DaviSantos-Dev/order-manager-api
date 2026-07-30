package davisantos.dev.OrderManager.modules.order.dto;

import davisantos.dev.OrderManager.modules.user.domain.User;

import java.util.List;

public record OrderResponse(
        Long id,
        User client,
        List<OrderItemResponse> orderItems,
        String status
) {
}
