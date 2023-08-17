package de.firecreeper82.shop.entity

import de.firecreeper82.shop.model.OrderStatus
import de.firecreeper82.shop.repository.OrderPositionEntity
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

    @ElementCollection
    @CollectionTable(name = "ORDER_POSITIONS", joinColumns = [JoinColumn(name = "orderId", referencedColumnName = "ID")])
    val orderPositions: List<OrderPositionEntity>
)