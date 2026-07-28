package davisantos.dev.OrderManager.modules.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull
        Long productId,
        @NotNull
        int quantity
) {
}
