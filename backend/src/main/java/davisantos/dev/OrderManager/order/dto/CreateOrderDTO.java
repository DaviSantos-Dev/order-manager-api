package davisantos.dev.OrderManager.order.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateOrderDTO {

    @NotEmpty
    private Long clientId;
}
