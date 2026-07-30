package davisantos.dev.OrderManager.modules.user.dto;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
