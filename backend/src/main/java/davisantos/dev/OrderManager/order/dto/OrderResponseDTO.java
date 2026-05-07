package davisantos.dev.OrderManager.order.dto;

import davisantos.dev.OrderManager.order.domain.Order;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDTO {

    private Long id;
    private String client;
    private List<OrderItemResponseDTO> orderItems;
    private String status;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.client = order.getClient();
        this.status = order.getStatus().name();
        this.orderItems = order.getOrderItems().stream().map(OrderItemResponseDTO::new).toList();
    }
}
