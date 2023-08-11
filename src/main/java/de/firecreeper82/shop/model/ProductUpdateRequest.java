package de.firecreeper82.shop.model;

public record ProductUpdateRequest(String name, String description, Integer priceInCent) {
}
