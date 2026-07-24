package davisantos.dev.OrderManager.modules.order.dto;

public record OrderItemRequest(
        Long productId,
        int quantity
) {
}
