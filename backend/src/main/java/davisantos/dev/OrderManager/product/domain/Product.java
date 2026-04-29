package davisantos.dev.OrderManager.product.domain;

import davisantos.dev.OrderManager.product.domain.enums.ProductStatus;
import davisantos.dev.OrderManager.product.domain.exceptions.InvalidStateException;
import davisantos.dev.OrderManager.product.domain.exceptions.InvalidValueException;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(precision=10, scale=2)
    private BigDecimal price;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public Product(String name, BigDecimal price, int quantity) {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        this.status = ProductStatus.AVAILABLE;
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            throw new InvalidValueException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        if (price == null) {
            throw new InvalidValueException("Price cannot be empty");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidValueException("Price must be greater than zero");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidValueException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public void setAvailable(){
        if (this.quantity <= 0){
            throw new InvalidStateException("Product cannot be available without stock");
        }
        this.status = ProductStatus.AVAILABLE;
    }
    public void setUnavailable(){
        if (this.quantity > 0){
            throw new InvalidStateException("Product cannot be unavailable having stock");
        }
        if (this.status != ProductStatus.AVAILABLE) {
            throw  new InvalidStateException("Product is already unavailable");
        }
        this.status = ProductStatus.UNAVAILABLE;
    }
    public void setDisabled(){
        this.status = ProductStatus.DISABLED;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", name='" + name + '\'' +
                "\n, price=" + price +
                "\n, quantity=" + quantity +
                "\n, status=" + status ;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0){
            throw new InvalidValueException("The increased quantity must be grater than zero");
        }
        this.quantity += quantity;
        if (this.status == ProductStatus.UNAVAILABLE) {
            setAvailable();
        }
    }

    public void decreaseQuantity(int quantity) {
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
