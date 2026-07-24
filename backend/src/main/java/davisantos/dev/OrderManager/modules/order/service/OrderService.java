package davisantos.dev.OrderManager.modules.order.service;

import davisantos.dev.OrderManager.modules.order.domain.Order;
import davisantos.dev.OrderManager.modules.order.domain.enums.OrderStatus;
import davisantos.dev.OrderManager.modules.order.dto.CreateOrderDTO;
import davisantos.dev.OrderManager.modules.order.dto.OrderResponseDTO;
import davisantos.dev.OrderManager.modules.order.repository.OrderRepository;
import davisantos.dev.OrderManager.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private OrderRepository orderRepository;

    public OrderResponseDTO create(CreateOrderDTO dto){
        //Será transformado em consulta ao Repository da entidade cliente quando o mesmo for implementado
        String client = dto.getClientId().toString();
        Order order = Order.builder().client(client).status(OrderStatus.PENDING).build();
        return new OrderResponseDTO(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Error: Cannot found this order"));
        return new OrderResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAll(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderResponseDTO::new).toList();
    }

    /*public OrderResponseDTO update(Long id, CreateOrderDTO dto){

    }

    public OrderResponseDTO updateOrderItem(Long orderId,Long itemId, CreateOrderDTO dto){

    }*/

}
