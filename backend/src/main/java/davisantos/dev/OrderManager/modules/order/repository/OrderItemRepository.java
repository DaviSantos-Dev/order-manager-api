package davisantos.dev.OrderManager.modules.order.repository;

import davisantos.dev.OrderManager.modules.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderId(Long orderId);
}
