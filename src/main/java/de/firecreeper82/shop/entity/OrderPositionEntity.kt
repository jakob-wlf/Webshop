package de.firecreeper82.shop.entity

import jakarta.persistence.Embeddable

@Embeddable
data class OrderPositionEntity(
    val id: String,
    val productId: String,
    val quantity: Long
)
