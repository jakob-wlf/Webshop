package de.firecreeper82.shop.model

import jakarta.persistence.Id
import java.time.LocalDateTime

data class GetOrderResponse(
    val id: String,
    val orderTime: LocalDateTime,
    val status: OrderStatus,
    val customer: CustomerResponse,
    val orderPositions: List<GetOrderPositionResponse>
)

class GetOrderPositionResponse(
    @Id val id: String,
    val product: ProductResponse,
    val quantity: Long
)
