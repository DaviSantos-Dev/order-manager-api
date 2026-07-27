package davisantos.dev.OrderManager.modules.order.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
        @NotEmpty
        Long clientId,
        List<OrderItemRequest> orderItems
) {
}
