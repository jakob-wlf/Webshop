package de.firecreeper82.shop.repository

import jakarta.persistence.Embeddable

@Embeddable
data class OrderPositionEntity(
    val id: String,
    val productId: String,
    val quantity: Long
)
