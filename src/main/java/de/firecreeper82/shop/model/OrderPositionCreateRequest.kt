package de.firecreeper82.shop.model

data class OrderPositionCreateRequest(
        val productId: String,
        val quantity: Long
)