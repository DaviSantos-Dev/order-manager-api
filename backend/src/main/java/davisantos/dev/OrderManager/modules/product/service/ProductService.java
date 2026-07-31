package davisantos.dev.OrderManager.modules.product.service;

import davisantos.dev.OrderManager.modules.product.domain.Product;
import davisantos.dev.OrderManager.modules.product.domain.enums.ProductStatus;
import davisantos.dev.OrderManager.modules.product.dto.*;
import davisantos.dev.OrderManager.modules.product.mapper.ProductMapper;
import davisantos.dev.OrderManager.modules.product.repository.ProductRepository;

import davisantos.dev.OrderManager.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductResponse create(ProductRequest dto) {
        return mapper.toDto(
            repository.save(
                Product.builder()
                        .name(dto.name())
                        .price(dto.price())
                        .quantity(dto.quantity())
                        .active(true)
                        .status(ProductStatus.AVAILABLE)
                        .build()
            )
        );
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Error: Product not found"));
        return mapper.toDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ProductResponse update(Long id, ProductRequest dto) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Error: Product not found"));

        product.setName(dto.name());
        product.setPrice(dto.price());

        return mapper.toDto(product);
    }

    public void softDeleteById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Error: Product not found"));
        product.deactivateProduct();
    }

    public void increaseStock(Long id, int quantity) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Error: Product not found"));
        product.increaseQuantity(quantity);
    }
    
    public void decreaseStock(Long id, int quantity) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Error: Product not found"));
        product.decreaseQuantity(quantity);
    }
}
