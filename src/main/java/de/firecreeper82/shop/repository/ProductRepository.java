package de.firecreeper82.shop.repository;

import de.firecreeper82.shop.model.ProductCreateRequest;
import de.firecreeper82.shop.model.ProductResponse;

import java.util.*;
import java.util.stream.Collectors;

public class ProductRepository {

    List<ProductResponse> products;

    public ProductRepository() {
        products = new ArrayList<>();
        products.add(new ProductResponse(UUID.randomUUID().toString(), "AMD Ryzen 9 5950X", "Just some stuff", 79900, List.of("amd", "processor")));
        products.add(new ProductResponse(UUID.randomUUID().toString(), "Intel Core 19-9900KF", "Very cool stuff", 74353, List.of("intel", "processor")));
        products.add(new ProductResponse(UUID.randomUUID().toString(), "NVIDIA Geforce GTA 1080 Ti Black Edition 11Gb", "Super cool", 74544, List.of("nvidia", "graphics")));
    }

    public List<ProductResponse> findAll(String tag) {
        if(tag == null)  {
            return products;
        }
        else {
            return products
                    .stream()
                    .filter(p -> lowercaseTags(p).contains(tag.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }
    private List<String> lowercaseTags(ProductResponse p) {
        return p.getTags()
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> findById(String id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void deleteById(String id) {
        this.products = products.stream()
                                .filter(p -> !Objects.equals(p.getId(), id))
                                .collect(Collectors.toList());
    }

    public ProductResponse save(ProductCreateRequest request) {
        ProductResponse response = new ProductResponse(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getDescription(),
                request.getPriceInCent(),
                request.getTags());
        products.add(response);
        return response;
    }
}
