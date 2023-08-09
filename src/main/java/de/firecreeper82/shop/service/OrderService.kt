package de.firecreeper82.shop.service

import de.firecreeper82.shop.model.*
import de.firecreeper82.shop.repository.CustomerRepository
import de.firecreeper82.shop.repository.OrderPositionRepository
import de.firecreeper82.shop.repository.OrderRepository
import de.firecreeper82.shop.repository.ProductRepository
import java.util.UUID

class OrderService {

    var orderRepository = OrderRepository()
    val customerRepository = CustomerRepository()
    val orderPositionRepository = OrderPositionRepository()
    val productRepository = ProductRepository()

    fun createOrder(request: OrderCreateRequest): OrderResponse {

        val customer: CustomerResponse = customerRepository.findById(request.customerId) ?: throw Exception("Customer not found")

        return orderRepository.save(request)
    }

    fun createNewPositionForOrder(orderId: String, request: OrderPositionCreateRequest): OrderPositionResponse {

        orderRepository.findById(orderId) ?: throw Exception("Order not found")

        if (productRepository.findById(request.productId).isEmpty)
            throw Exception("Product not found")

        val orderPositionResponse = OrderPositionResponse(
                id = UUID.randomUUID().toString(),
                productId = request.productId,
                quantity = request.quantity

        )
        orderPositionRepository.save(orderPositionResponse)

        return orderPositionResponse;

    }

}