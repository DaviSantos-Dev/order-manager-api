package davisantos.dev.OrderManager.order.service;

import davisantos.dev.OrderManager.order.domain.Order;
import davisantos.dev.OrderManager.order.domain.exceptions.OrderNotFoundException;
import davisantos.dev.OrderManager.order.dto.CreateOrderDTO;
import davisantos.dev.OrderManager.order.dto.OrderResponseDTO;
import davisantos.dev.OrderManager.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponseDTO create(CreateOrderDTO dto){
        //Será transformado em consulta ao orderRepository da entidade cliente quando o mesmo for implementado
        String client = dto.getClientId().toString();
        Order order = new Order(client);
        return new OrderResponseDTO(orderRepository.save(order));
    }

    public OrderResponseDTO findById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Error: Cannot found this order"));
        return new OrderResponseDTO(order);
    }

    public List<OrderResponseDTO> findAll(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderResponseDTO::new).toList();
    }

    /*public OrderResponseDTO update(Long id, CreateOrderDTO dto){

    }

    public OrderResponseDTO updateOrderItem(Long orderId,Long itemId, CreateOrderDTO dto){

    }*/

}
