package davisantos.dev.OrderManager.modules.order.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotNull
        Long clientId
) {
}
