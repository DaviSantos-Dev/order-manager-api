package davisantos.dev.OrderManager.modules.product.service;

import davisantos.dev.OrderManager.modules.product.domain.Product;
import davisantos.dev.OrderManager.modules.product.dto.CreateProductDTO;
import davisantos.dev.OrderManager.modules.product.dto.ProductResponseDTO;
import davisantos.dev.OrderManager.modules.product.dto.UpdateProductDTO;
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

    public ProductResponseDTO create(CreateProductDTO dto) {
        Product product = Product.builder().name(dto.getName()).price(dto.getPrice()).quantity(dto.getQuantity()).build();
        return new ProductResponseDTO(repository.save(product));
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Error: Product not found"));
        return new ProductResponseDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    public ProductResponseDTO update(Long id, UpdateProductDTO dto) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Error: Product not found"));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        return new ProductResponseDTO(product);
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
