package davisantos.dev.OrderManager.order.domain;

import davisantos.dev.OrderManager.product.domain.Product;
import davisantos.dev.OrderManager.order.domain.Order;
import davisantos.dev.OrderManager.shared.exceptions.InvalidValueException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(precision=19, scale=2)
    private BigDecimal unitPrice;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Product product, int quantity) {
        setProduct(product);
        this.unitPrice = product.getPrice();
        setQuantity(quantity);
    }

    private void setProduct(Product product) {
        if (product == null) {
            throw new InvalidValueException("Product cannot be null");
        }
        this.product = product;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidValueException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
    }

    // Nível de Acesso Default (Somente o mesmo pacote)
    void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal calculateSubTotal(){
        if (unitPrice == null) {
            throw new InvalidValueException("Product cannot be null.");
        }
        return this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
