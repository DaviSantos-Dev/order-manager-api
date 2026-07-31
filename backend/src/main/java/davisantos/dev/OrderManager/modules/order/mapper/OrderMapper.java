package davisantos.dev.OrderManager.modules.order.mapper;

import davisantos.dev.OrderManager.modules.order.domain.Order;
import davisantos.dev.OrderManager.modules.order.dto.OrderResponse;
import davisantos.dev.OrderManager.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {OrderItemMapper.class, UserMapper.class}
)
public interface OrderMapper {

    @Mapping(target = "total", expression = "java(order.calculateTotal())")
    OrderResponse toDto(Order order);
}
