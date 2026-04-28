package davisantos.dev.OrderManager.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UpdateProductDTO {

    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
}
