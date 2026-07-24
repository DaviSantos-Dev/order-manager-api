package davisantos.dev.OrderManager.modules.product.mapper;

import davisantos.dev.OrderManager.modules.product.domain.Product;
import davisantos.dev.OrderManager.modules.product.dto.ProductRequest;
import davisantos.dev.OrderManager.modules.product.dto.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper{

    Product toEntity(ProductRequest dto);

    ProductResponse toDto(Product dto);
}
