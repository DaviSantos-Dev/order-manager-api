package davisantos.dev.OrderManager.modules.product.controller;

import davisantos.dev.OrderManager.modules.product.dto.*;
import davisantos.dev.OrderManager.modules.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest dto) {
        ProductResponse product = service.create(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.id()).toUri();

        return ResponseEntity.created(uri).body(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequest dto) {
        ProductResponse response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }
}
