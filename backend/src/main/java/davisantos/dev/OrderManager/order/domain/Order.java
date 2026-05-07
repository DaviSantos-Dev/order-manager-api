package davisantos.dev.OrderManager.order.domain;

import davisantos.dev.OrderManager.order.domain.enums.OrderStatus;
import davisantos.dev.OrderManager.order.domain.exceptions.EmptyOrderException;
import davisantos.dev.OrderManager.product.domain.Product;
import davisantos.dev.OrderManager.order.domain.exceptions.OrderItemNotFoundException;
import davisantos.dev.OrderManager.shared.exceptions.InvalidStateException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED )
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String client;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(String client) {
        this.client = client;
        this.status = OrderStatus.PENDING;
    }

    public void addItem(Product product, int quantity) {
        OrderItem orderItem = new OrderItem(product, quantity);
        orderItem.setOrder(this);
        this.orderItems.add(orderItem);
    }

    public void removeItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    public OrderItem findItemById(Long id){
        OrderItem item = orderItems.stream().filter(
                orderItem -> orderItem.getId().equals(id)).findFirst().orElseThrow(() -> new OrderItemNotFoundException("Item not found"));
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
        if (orderItems.size() == 0){
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
