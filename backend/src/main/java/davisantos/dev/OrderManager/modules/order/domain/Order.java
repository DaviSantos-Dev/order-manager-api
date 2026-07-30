package davisantos.dev.OrderManager.modules.order.domain;

import davisantos.dev.OrderManager.modules.order.domain.enums.OrderStatus;
import davisantos.dev.OrderManager.modules.order.domain.exceptions.EmptyOrderException;
import davisantos.dev.OrderManager.modules.user.domain.User;
import davisantos.dev.OrderManager.shared.exceptions.InvalidStateException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders_tb")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne
    private User client;
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<OrderItem> orderItems = new HashSet<>();
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public void addItem(OrderItem item) {
        this.orderItems.add(item);
    }

    public void removeItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
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

    public void reopenOrder(){
        if (this.status == OrderStatus.PAID) {
            throw new InvalidStateException("Order has been paid");
        }
        if (this.status == OrderStatus.PENDING) {
            throw new InvalidStateException("Order is already open");
        }
        this.status = OrderStatus.PENDING;
    }
}
