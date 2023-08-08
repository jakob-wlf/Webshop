package de.firecreeper82.shop.controller;

import de.firecreeper82.shop.model.ProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts(@RequestParam String tag) {
        List<ProductResponse> products = List.of(
                new ProductResponse("1", "AMD Ryzen 9 5950X", "Just some stuff", 79900, List.of("amd", "processor")),
                new ProductResponse("2", "Intel Core 19-9900KF", "Very cool stuff", 74353, List.of("intel", "processor")),
                new ProductResponse("3", "NVIDIA Geforce GTA 1080 Ti Black Edition 11Gb", "Super cool", 74544, List.of("Nvidia", "graphics"))

        );

        if(tag == null)  {
            return products;
        }
        else {
            return products.stream().filter(p -> p.getTags().contains(tag.toLowerCase())).collect(Collectors.toList());
        }
    }

}
