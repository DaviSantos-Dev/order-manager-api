package davisantos.dev.OrderManager.modules.order.repository;

import davisantos.dev.OrderManager.modules.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
