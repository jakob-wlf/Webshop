package de.firecreeper82.shop.model;

import java.util.List;

public class ProductCreateRequest {

    private final String name;
    private final String description;
    private final Integer priceInCent;
    private final List<String> tags;

    public ProductCreateRequest(String name, String description, Integer priceInCent, List<String> tags) {
        this.name = name;
        this.description = description;
        this.priceInCent = priceInCent;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriceInCent() {
        return priceInCent;
    }

    public List<String> getTags() {
        return tags;
    }
}
