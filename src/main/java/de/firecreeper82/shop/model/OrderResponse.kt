package de.firecreeper82.shop.model

import java.time.LocalDateTime

data class OrderResponse(val id: String, val customerId: String, val orderTime: LocalDateTime, val orderStatus: OrderStatus, val orderPositions: List<OrderPositionResponse>)

enum class OrderStatus {
    NEW, CONFIRMED, SENT, DELIVERED, CANCELED
}

data class OrderPositionResponse(val id: String, val productId: String, val quantity: Long)