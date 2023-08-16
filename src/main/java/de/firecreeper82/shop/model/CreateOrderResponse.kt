package de.firecreeper82.shop.model

import java.time.LocalDateTime

data class CreateOrderResponse(val id: String, val customerId: String, val orderTime: LocalDateTime, val orderStatus: OrderStatus, val orderPositions: List<OrderPositionResponse>)

enum class OrderStatus {
    NEW, CONFIRMED, SENT, DELIVERED, CANCELED
}

data class OrderPositionResponse(val id: String, val orderId: String, val productId: String, val quantity: Long)