package de.firecreeper82.shop.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

interface OrderPositionRepository : JpaRepository<OrderPositionEntity, String>

@Entity
@Table(name="order_positions")
data class OrderPositionEntity(
    @Id val id: String,
    val orderId: String,
    val productId: String,
    val quantity: Long
)
