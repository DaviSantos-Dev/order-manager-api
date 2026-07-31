package davisantos.dev.OrderManager.modules.order.mapper;

import davisantos.dev.OrderManager.modules.order.domain.OrderItem;
import davisantos.dev.OrderManager.modules.order.dto.OrderItemResponse;
import davisantos.dev.OrderManager.modules.product.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = ProductMapper.class
)
public interface OrderItemMapper {

    @Mapping(target = "subTotal", expression = "java(orderItem.calculateSubTotal())")
    OrderItemResponse toDto(OrderItem orderItem);
}
