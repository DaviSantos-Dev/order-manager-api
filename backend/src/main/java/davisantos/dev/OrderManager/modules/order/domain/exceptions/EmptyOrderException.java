package davisantos.dev.OrderManager.modules.order.domain.exceptions;

public class EmptyOrderException extends RuntimeException {
    public EmptyOrderException(String message) {
        super(message);
    }
}
