package davisantos.dev.OrderManager.modules.order.controller;

import davisantos.dev.OrderManager.modules.order.dto.OrderItemRequest;
import davisantos.dev.OrderManager.modules.order.dto.OrderItemResponse;
import davisantos.dev.OrderManager.modules.order.dto.OrderRequest;
import davisantos.dev.OrderManager.modules.order.dto.OrderResponse;
import davisantos.dev.OrderManager.modules.order.service.OrderService;
import davisantos.dev.OrderManager.shared.utils.GenericController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements GenericController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemResponse>> findItemsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrderItemsByOrderId(orderId));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest dto) {
        OrderResponse response = orderService.create(dto);
        return ResponseEntity.created(generateUri(response.id())).body(response);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItemResponse> addOrderItem(@PathVariable Long orderId, @Valid @RequestBody OrderItemRequest dto){
        OrderItemResponse response = orderService.addItem(orderId, dto);
        return ResponseEntity.created(generateUri(response.id())).body(response);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(@PathVariable Long orderId, @PathVariable Long itemId, @Valid @RequestBody OrderItemRequest dto){
        OrderItemResponse response = orderService.updateItem(orderId, itemId, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{orderId}/items/{itemId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long orderId, @PathVariable Long itemId){
        orderService.removeItem(orderId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponse> payOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.payOrder(orderId));
    }
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
    @PostMapping("/{orderId}/reopen")
    public ResponseEntity<OrderResponse> reopenOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.reopenOrder(orderId));
    }

}
