package davisantos.dev.OrderManager.modules.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String username,
        @NotBlank String email
) {
}
