package davisantos.dev.OrderManager.order.dto;

import davisantos.dev.OrderManager.order.domain.OrderItem;
import davisantos.dev.OrderManager.product.dto.ProductResponseDTO;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItemResponseDTO {

    private Long id;
    private ProductResponseDTO product;
    private BigDecimal price;
    private int quantity;
    private OrderResponseDTO order;

    public OrderItemResponseDTO(OrderItem orderItem){
        this.id = orderItem.getId();
        this.product  = new ProductResponseDTO(orderItem.getProduct());
        this.price = orderItem.getUnitPrice();
        this.quantity = orderItem.getQuantity();
        this.order = new OrderResponseDTO(orderItem.getOrder());
    }
}
