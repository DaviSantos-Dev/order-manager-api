package davisantos.dev.OrderManager.modules.order.dto;

import java.util.List;

public record OrderResponse(
        Long id,
        String client,
        List<OrderItemResponse> orderItems,
        String status
) {
}
