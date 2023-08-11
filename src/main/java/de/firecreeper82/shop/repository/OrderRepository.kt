package de.firecreeper82.shop.repository

import de.firecreeper82.shop.exceptions.IdNotFoundException
import de.firecreeper82.shop.model.OrderCreateRequest
import de.firecreeper82.shop.model.OrderResponse
import de.firecreeper82.shop.model.OrderStatus
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
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

    fun save(response: OrderResponse): OrderResponse {
        orders.add(response)
        return response
    }

    fun findById(orderId: String): OrderResponse {
        return orders.find { it.id == orderId }
            ?: throw IdNotFoundException(message = "Order with id $orderId not found", statusCode = HttpStatus.BAD_REQUEST)
    }

    fun findAllByCustomerIdWhereOrderStatusIsNew(customerId: String): List<OrderResponse> {
        return orders.filter { it.customerId == customerId && it.orderStatus == OrderStatus.NEW }
    }
}