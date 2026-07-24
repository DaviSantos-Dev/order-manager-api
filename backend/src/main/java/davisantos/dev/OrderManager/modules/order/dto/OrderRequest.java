package davisantos.dev.OrderManager.modules.order.dto;

import jakarta.validation.constraints.NotEmpty;

public record OrderRequest(
        @NotEmpty
        Long clientId
) {
}
