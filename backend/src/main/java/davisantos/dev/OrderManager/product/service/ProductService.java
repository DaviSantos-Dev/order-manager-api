package davisantos.dev.OrderManager.product.service;

import davisantos.dev.OrderManager.product.domain.Product;
import davisantos.dev.OrderManager.product.domain.exceptions.ProductNotFoundException;
import davisantos.dev.OrderManager.product.dto.CreateProductDTO;
import davisantos.dev.OrderManager.product.dto.ProductResponseDTO;
import davisantos.dev.OrderManager.product.dto.UpdateProductDTO;
import davisantos.dev.OrderManager.product.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public void create(CreateProductDTO dto) {
        Product product = new Product(dto.getName(), dto.getPrice(), dto.getQuantity());
        repository.save(product);
    }

    public ProductResponseDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Error: Product not found"));
        return new ProductResponseDTO(product);
    }

    public List<ProductResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    @Transactional
    public void update(Long id, UpdateProductDTO dto) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Error: Product not found"));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

    }

    @Transactional
    public void softDeleteById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Error: Product not found"));
        product.setDisabled();
    }

    @Transactional
    public void increaseStock(Long id, int quantity) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Error: Product not found"));
        product.increaseQuantity(quantity);
    }
    @Transactional
    public void decreaseStock(Long id, int quantity) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Error: Product not found"));
        product.decreaseQuantity(quantity);
    }
}
