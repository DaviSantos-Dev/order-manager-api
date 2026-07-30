package davisantos.dev.OrderManager.modules.user.mapper;

import davisantos.dev.OrderManager.modules.user.domain.User;
import davisantos.dev.OrderManager.modules.user.dto.UserRequest;
import davisantos.dev.OrderManager.modules.user.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest dto);

    UserResponse toDto (User entity);
}
