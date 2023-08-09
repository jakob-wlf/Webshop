package de.firecreeper82.shop.repository

import de.firecreeper82.shop.model.OrderCreateRequest
import de.firecreeper82.shop.model.OrderResponse
import de.firecreeper82.shop.model.OrderStatus
import de.firecreeper82.shop.model.ProductResponse
import java.time.LocalDateTime
import java.util.*
import java.util.function.Predicate

class OrderRepository {

    val orders = mutableListOf<OrderResponse>();
    fun save(request: OrderCreateRequest): OrderResponse {
        val orderResponse = OrderResponse(
                id = UUID.randomUUID().toString(),
                customerId = request.customerId,
                orderTime = LocalDateTime.now(),
                orderStatus = OrderStatus.NEW,
                orderPositions = emptyList()
        );

        orders.add(orderResponse)
        return orderResponse;
    }

    fun findById(orderId: String): OrderResponse? {
        return orders.find { it.id == orderId };
    }
}