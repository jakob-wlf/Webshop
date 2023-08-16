package de.firecreeper82.shop.controller;

import de.firecreeper82.shop.exceptions.IdNotFoundException;
import de.firecreeper82.shop.model.ProductCreateRequest;
import de.firecreeper82.shop.model.ProductResponse;
import de.firecreeper82.shop.model.ProductUpdateRequest;
import de.firecreeper82.shop.repository.ProductEntity;
import de.firecreeper82.shop.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ProductController {


    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts(@RequestParam(required = false) String tag ) {
        return productRepository.findAll()
                .stream()
                .filter(productEntity -> tag == null || productEntity.getTags().contains(tag))
                .map(ProductController::mapToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public ProductResponse getProductById(@PathVariable String id) {
        ProductEntity product = productRepository.getReferenceById(id);

        return mapToResponse(product);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products")
    public ProductResponse createProduct(@RequestBody ProductCreateRequest request) {
        ProductEntity product = new ProductEntity(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getDescription(),
                request.getPriceInCent(),
                request.getTags()
        );

        ProductEntity savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    @PutMapping("/products/{id}")
    public ProductResponse updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable String id) throws IdNotFoundException {
        final ProductEntity product = productRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Product with id " + id + " not found", HttpStatus.BAD_REQUEST));
        final ProductEntity updatedProduct = new ProductEntity(
                product.getId(),
                (request.name() == null) ? product.getName() : request.name(),
                (request.description() == null) ? product.getDescription() : request.description(),
                (request.priceInCent() == null) ? product.getPriceInCent() : request.priceInCent(),
                product.getTags()
        );

        ProductEntity savedProduct = productRepository.save(updatedProduct);
        return mapToResponse(savedProduct);
    }

    @NotNull
    private static ProductResponse mapToResponse(ProductEntity product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPriceInCent(),
                product.getTags()
        );
    }
}
