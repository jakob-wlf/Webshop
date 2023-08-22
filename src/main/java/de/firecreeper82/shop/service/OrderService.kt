package de.firecreeper82.shop.service

import de.firecreeper82.shop.entity.OrderEntity
import de.firecreeper82.shop.entity.OrderPositionEntity
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
                   val customerRepository: CustomerRepository) {

    fun createOrder(request: OrderCreateRequest): CreateOrderResponse {

        customerRepository.findById(request.customerId)

        val order = OrderEntity(
            id = UUID.randomUUID().toString(),
            customerId = request.customerId,
            orderTime = LocalDateTime.now(),
            orderStatus = OrderStatus.NEW,
            orderPositions = emptyList()
        )

        val savedOrder = orderRepository.save(order)

        return mapToResponse(savedOrder)
    }

    fun createNewPositionForOrder(orderId: String, request: OrderPositionCreateRequest): OrderPositionResponse {

        val order = orderRepository.getReferenceById(orderId)

        if (productRepository.findById(request.productId).isEmpty)
            throw IdNotFoundException(message = "Product with id ${request.productId} not found", statusCode = HttpStatus.BAD_REQUEST)

        val orderPosition = OrderPositionEntity(
                id = UUID.randomUUID().toString(),
                productId = request.productId,
                quantity = request.quantity
        )
        val updatedOrderPositions = order.orderPositions.plus(orderPosition)
        val updatedOrder = order.copy(
            orderPositions = updatedOrderPositions
        )
        orderRepository.save(updatedOrder)

        return mapToResponse(orderPosition)
    }

    fun updateOrder(id: String, request: OrderUpdateRequest): CreateOrderResponse {
        val order = orderRepository.getReferenceById(id)
        val updatedOrder = order.copy(orderStatus = request.orderStatus)
        val savedOrder = orderRepository.save(updatedOrder)
        return mapToResponse(savedOrder)
    }

    private fun mapToResponse(savedOrder: OrderEntity) = CreateOrderResponse(
        id = savedOrder.id,
        customerId = savedOrder.customerId,
        orderTime = savedOrder.orderTime,
        orderStatus = savedOrder.orderStatus,
        orderPositions = emptyList()
    )

    fun getOrder(id: String): GetOrderResponse {
        val order = orderRepository.getReferenceById(id)
        val customer = customerRepository.getReferenceById(order.customerId)

        val orderPositions = order
            .orderPositions
            .map {
                val productEntity = productRepository.getReferenceById(it.productId)

                GetOrderPositionResponse(
                    id = it.id,
                    quantity = it.quantity,
                    product = ProductResponse(
                        productEntity.id,
                        productEntity.name,
                        productEntity.description,
                        productEntity.priceInCent,
                        productEntity.tags
                    )
                )
            }

        return GetOrderResponse(
            id = order.id,
            status = order.orderStatus,
            orderTime = order.orderTime,
            customer = CustomerResponse(
                id = customer.id,
                firstName = customer.firstName,
                lastName = customer.lastName,
                email = customer.email
            ),
            orderPositions = orderPositions
        )
    }

    companion object {
        fun mapToResponse(savedOrder: OrderPositionEntity) =
            OrderPositionResponse(
                id = savedOrder.id,

                productId = savedOrder.productId,
                quantity = savedOrder.quantity
            )
    }

}