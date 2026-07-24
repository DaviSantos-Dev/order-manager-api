package davisantos.dev.OrderManager.modules.order.dto;

public record OrderResponse(
        Long id,
        String client,
        list<OrderItemResponse> orderItems,
        String status
) {
}
