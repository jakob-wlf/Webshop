package de.firecreeper82.shop.model;

import java.util.List;

public record ProductResponse(String id, String name, String description, Integer priceInCent, List<String> tags) {}