package de.firecreeper82.shop.repository;

import de.firecreeper82.shop.model.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProductRepository {

    List<ProductResponse> products = List.of(
            new ProductResponse("1", "AMD Ryzen 9 5950X", "Just some stuff", 79900, List.of("amd", "processor")),
            new ProductResponse("2", "Intel Core 19-9900KF", "Very cool stuff", 74353, List.of("intel", "processor")),
            new ProductResponse("3", "NVIDIA Geforce GTA 1080 Ti Black Edition 11Gb", "Super cool", 74544, List.of("nvidia", "graphics"))

    );

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
}
