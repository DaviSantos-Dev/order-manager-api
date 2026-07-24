package davisantos.dev.OrderManager.modules.order.domain;

import davisantos.dev.OrderManager.modules.order.domain.enums.OrderStatus;
import davisantos.dev.OrderManager.modules.order.domain.exceptions.EmptyOrderException;
import davisantos.dev.OrderManager.modules.product.domain.Product;
import davisantos.dev.OrderManager.shared.exceptions.InvalidStateException;
import davisantos.dev.OrderManager.shared.exceptions.NotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
    private String client;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public void addItem(Product product, int quantity) {
        OrderItem orderItem = OrderItem.builder().product(product).quantity(quantity).build();
        orderItem.setOrder(this);
        this.orderItems.add(orderItem);
    }

    public void removeItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    public OrderItem findItemById(Long id){
        OrderItem item = orderItems.stream().filter(
                orderItem -> orderItem.getId().equals(id)).findFirst().orElseThrow(() -> new NotFoundException("Item not found"));
        return item;
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            total = total.add(orderItem.calculateSubTotal());
        }
        return total;
    }

    public void payOrder(){
        if (this.status == OrderStatus.PAID) {
            throw new InvalidStateException("Order has already been paid");
        }
        if (this.status == OrderStatus.CANCELLED) {
            throw new InvalidStateException("Order has been cancelled");
        }
        if (orderItems.isEmpty()){
            throw new EmptyOrderException("You cannot pay an empty order");
        }
        this.status = OrderStatus.PAID;
    }

    public void cancelOrder(){
        if (this.status == OrderStatus.PAID) {
            throw new InvalidStateException("Order has been paid");
        }
        if (this.status == OrderStatus.CANCELLED) {
            throw new InvalidStateException("Order has already been cancelled");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void reOpenOrder(){
        if (this.status == OrderStatus.PAID) {
            throw new InvalidStateException("Order has been paid");
        }
        if (this.status == OrderStatus.PENDING) {
            throw new InvalidStateException("Order is already open");
        }
        this.status = OrderStatus.PENDING;
    }
}
