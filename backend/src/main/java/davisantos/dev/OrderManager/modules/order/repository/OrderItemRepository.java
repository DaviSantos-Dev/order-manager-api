package davisantos.dev.OrderManager.modules.order.repository;

import davisantos.dev.OrderManager.modules.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
