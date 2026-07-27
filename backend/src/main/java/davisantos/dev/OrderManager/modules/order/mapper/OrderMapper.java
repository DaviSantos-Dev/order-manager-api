package davisantos.dev.OrderManager.modules.order.mapper;

import davisantos.dev.OrderManager.modules.order.domain.Order;
import davisantos.dev.OrderManager.modules.order.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = OrderItemMapper.class
)
public interface OrderMapper {

    OrderResponse toDto(Order order);
}
