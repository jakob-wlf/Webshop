package de.firecreeper82.shop.service

import de.firecreeper82.shop.exceptions.IdNotFoundException
import de.firecreeper82.shop.model.*
import de.firecreeper82.shop.repository.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class OrderService(val productRepository: ProductRepository,
                   val orderRepository: OrderRepository,
                   val customerRepository: CustomerRepository,
                   val orderPositionRepository: OrderPositionRepository) {

    fun createOrder(request: OrderCreateRequest): OrderResponse {

        customerRepository.findById(request.customerId)

        val order = OrderEntity(
            id = UUID.randomUUID().toString(),
            customerId = request.customerId,
            orderTime = LocalDateTime.now(),
            orderStatus = OrderStatus.NEW,
        )

        val savedOrder = orderRepository.save(order)

        return mapToResponse(savedOrder)
    }

    fun createNewPositionForOrder(orderId: String, request: OrderPositionCreateRequest): OrderPositionResponse {

        orderRepository.findById(orderId)

        if (productRepository.findById(request.productId).isEmpty)
            throw IdNotFoundException(message = "Product with id ${request.productId} not found", statusCode = HttpStatus.BAD_REQUEST)

        val orderPositon = OrderPositionEntity(
                id = UUID.randomUUID().toString(),
                orderId = orderId,
                productId = request.productId,
                quantity = request.quantity
        )
        val savedOrder = orderPositionRepository.save(orderPositon)

        return mapToResponse(savedOrder)
    }

    fun updateOrder(id: String, request: OrderUpdateRequest): OrderResponse {
        val order = orderRepository.getReferenceById(id)
        val updatedOrder = order.copy(orderStatus = request.orderStatus)
        val savedOrder = orderRepository.save(updatedOrder)
        return mapToResponse(savedOrder)
    }

    private fun mapToResponse(savedOrder: OrderEntity) = OrderResponse(
        id = savedOrder.id,
        customerId = savedOrder.customerId,
        orderTime = savedOrder.orderTime,
        orderStatus = savedOrder.orderStatus,
        orderPositions = emptyList()
    )

    companion object {
        fun mapToResponse(savedOrder: OrderPositionEntity) =
            OrderPositionResponse(
                id = savedOrder.id,
                orderId = savedOrder.orderId,
                productId = savedOrder.productId,
                quantity = savedOrder.quantity
            )
    }

}