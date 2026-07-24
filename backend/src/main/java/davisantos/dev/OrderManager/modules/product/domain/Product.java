package davisantos.dev.OrderManager.modules.product.domain;

import davisantos.dev.OrderManager.modules.product.domain.enums.ProductStatus;
import davisantos.dev.OrderManager.shared.exceptions.InvalidStateException;
import davisantos.dev.OrderManager.shared.exceptions.InvalidValueException;
import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(precision=10, scale=2,  nullable=false)
    private BigDecimal price;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private boolean active;

    private void setAvailable(){
        if (this.quantity <= 0){
            throw new InvalidStateException("Product cannot be available without stock");
        }
        this.status = ProductStatus.AVAILABLE;
    }

    private void setUnavailable(){
        if (this.quantity > 0){
            throw new InvalidStateException("Product cannot be unavailable having stock");
        }
        if (this.status != ProductStatus.AVAILABLE) {
            throw  new InvalidStateException("Product is already unavailable");
        }
        this.status = ProductStatus.UNAVAILABLE;
    }

    public void deactivateProduct(){
        if (!active){
            throw new InvalidStateException("This product is already deactivated");
        }
        this.active = false;
    }

    public void increaseQuantity(int quantity) {
        if (!active){
            throw new InvalidStateException("This product is deactivated");
        }
        if (quantity <= 0){
            throw new InvalidValueException("The increased quantity must be grater than zero");
        }
        this.quantity += quantity;
        if (this.status == ProductStatus.UNAVAILABLE) {
            setAvailable();
        }
    }

    public void decreaseQuantity(int quantity) {
        if (!active){
            throw new InvalidStateException("This product is deactivated");
        }
        if (quantity <= 0){
            throw new InvalidValueException("The decreased quantity must be greater than zero");
        }
        if (quantity > this.quantity){
            throw new InvalidStateException("Have not sufficient stock");
        }
        if (quantity == this.quantity){
            this.quantity -= quantity;
            setUnavailable();
        }
        else {
            this.quantity -= quantity;
        }
    }
}
