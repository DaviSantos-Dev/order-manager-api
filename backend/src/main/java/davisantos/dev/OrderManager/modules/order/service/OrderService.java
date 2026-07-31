package davisantos.dev.OrderManager.modules.order.service;

import davisantos.dev.OrderManager.modules.order.domain.Order;
import davisantos.dev.OrderManager.modules.order.domain.OrderItem;
import davisantos.dev.OrderManager.modules.order.domain.enums.OrderStatus;
import davisantos.dev.OrderManager.modules.order.dto.OrderItemRequest;
import davisantos.dev.OrderManager.modules.order.dto.OrderItemResponse;
import davisantos.dev.OrderManager.modules.order.dto.OrderRequest;
import davisantos.dev.OrderManager.modules.order.dto.OrderResponse;
import davisantos.dev.OrderManager.modules.order.mapper.OrderItemMapper;
import davisantos.dev.OrderManager.modules.order.mapper.OrderMapper;
import davisantos.dev.OrderManager.modules.order.repository.OrderItemRepository;
import davisantos.dev.OrderManager.modules.order.repository.OrderRepository;
import davisantos.dev.OrderManager.modules.product.domain.Product;
import davisantos.dev.OrderManager.modules.product.repository.ProductRepository;
import davisantos.dev.OrderManager.modules.user.domain.User;
import davisantos.dev.OrderManager.modules.user.repository.UserRepository;
import davisantos.dev.OrderManager.shared.exceptions.BusinessException;
import davisantos.dev.OrderManager.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderResponse create(OrderRequest dto){
        User client = userRepository.findById(dto.clientId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Order order = Order.builder()
                .client(client)
                .status(OrderStatus.PENDING)
                .orderItems(new HashSet<>())
                .build();
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Error: Cannot found this order"));
        return orderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<OrderItemResponse> findOrderItemsByOrderId(Long orderId){
        return orderItemRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public OrderItemResponse addItem(Long orderId, OrderItemRequest dto){
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order Not Found"));

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .quantity(dto.quantity())
                .order(order)
                .unitPrice(product.getPrice())
                .build();

        order.addItem(orderItem);
        return orderItemMapper.toDto(orderItem);
    }

    public void removeItem(Long itemId, Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order Not Found"));
        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item Not Found"));

        if (!orderItem.getOrder().equals(order)){
            throw new BusinessException("This item doesn't match this order");
        }
        order.removeItem(orderItem);
        orderItemRepository.delete(orderItem);
    }

    public OrderItemResponse updateItem(Long orderId, Long itemId, OrderItemRequest dto){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order Not Found"));
        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item Not Found"));
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        if (!orderItem.getOrder().equals(order)){
            throw new BusinessException("This item doesn't match this order");
        }
        orderItem.setQuantity(dto.quantity());
        orderItem.setProduct(product);
        orderItem.setUnitPrice(product.getPrice());

        return orderItemMapper.toDto(orderItem);
    }

    public OrderResponse payOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order Not Found"));
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);

        orderItems.forEach(orderItem -> {
            orderItem.getProduct()
                    .decreaseQuantity(orderItem.getQuantity());
        });
        order.payOrder();
        return orderMapper.toDto(order);
    }

    public OrderResponse cancelOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order Not Found"));

        order.cancelOrder();

        return orderMapper.toDto(order);
    }

    public OrderResponse reopenOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order Not Found"));

        order.reopenOrder();

        return orderMapper.toDto(order);
    }
}
