package davisantos.dev.OrderManager.order.repository;

import davisantos.dev.OrderManager.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
