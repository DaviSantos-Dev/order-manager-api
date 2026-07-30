package davisantos.dev.OrderManager.modules.order.domain;

import davisantos.dev.OrderManager.modules.product.domain.Product;
import davisantos.dev.OrderManager.shared.exceptions.InvalidValueException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "order_items_tb")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    @EqualsAndHashCode.Include
    private Product product;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(precision=19, scale=2, nullable=false)
    private BigDecimal unitPrice;
    @Column(nullable=false)
    private int quantity;

    public BigDecimal calculateSubTotal(){
        if (unitPrice == null) {
            throw new InvalidValueException("Product cannot be null.");
        }
        return this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
