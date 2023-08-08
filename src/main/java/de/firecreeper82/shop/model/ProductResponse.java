package de.firecreeper82.shop.model;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    private final String id;
    private final String name;
    private final String description;
    private final Integer priceInCent;
    private final List<String> tags;

    public ProductResponse(String id, String name, String description, Integer priceInCent, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priceInCent = priceInCent;
        this.tags = tags.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public String getId() {
        return id;
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
