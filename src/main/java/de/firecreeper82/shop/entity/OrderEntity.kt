package de.firecreeper82.shop.entity

import de.firecreeper82.shop.model.OrderStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id val id: String,
    val customerId: String,
    val orderTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus,
)