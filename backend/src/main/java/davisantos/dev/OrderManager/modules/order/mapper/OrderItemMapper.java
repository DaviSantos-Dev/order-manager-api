package davisantos.dev.OrderManager.modules.order.mapper;

import davisantos.dev.OrderManager.modules.order.domain.OrderItem;
import davisantos.dev.OrderManager.modules.order.dto.OrderItemResponse;
import davisantos.dev.OrderManager.modules.product.mapper.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = ProductMapper.class
)
public interface OrderItemMapper {

    OrderItemResponse toDto(OrderItem orderItem);
}
