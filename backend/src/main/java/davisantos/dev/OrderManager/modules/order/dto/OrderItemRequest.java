package davisantos.dev.OrderManager.modules.order.dto;

public record OrderItemRequest(
        Long productId,
        Long orderId,
        int quantity
) {
}
