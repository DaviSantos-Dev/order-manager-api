package davisantos.dev.OrderManager.product.dto;

import davisantos.dev.OrderManager.product.domain.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String status;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.status = product.getStatus().name();
    }
}
