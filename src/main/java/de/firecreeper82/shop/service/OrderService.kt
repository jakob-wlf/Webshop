package de.firecreeper82.shop.service

import de.firecreeper82.shop.exceptions.WebShopException
import de.firecreeper82.shop.model.*
import de.firecreeper82.shop.repository.CustomerRepository
import de.firecreeper82.shop.repository.OrderPositionRepository
import de.firecreeper82.shop.repository.OrderRepository
import de.firecreeper82.shop.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderService(val productRepository: ProductRepository,
                   val orderRepository: OrderRepository,
                   val customerRepository: CustomerRepository,
                   val orderPositionRepository: OrderPositionRepository) {

    fun createOrder(request: OrderCreateRequest): OrderResponse {

        val customer: CustomerResponse = customerRepository.findById(request.customerId)
            ?: throw WebShopException(message = "Customer with id ${request.customerId} not found", statusCode = HttpStatus.BAD_REQUEST)

        return orderRepository.save(request)
    }

    fun createNewPositionForOrder(orderId: String, request: OrderPositionCreateRequest): OrderPositionResponse {

        orderRepository.findById(orderId)
            ?: throw WebShopException(message = "Order with id $orderId not found", statusCode = HttpStatus.BAD_REQUEST)

        if (productRepository.findById(request.productId).isEmpty)
            throw WebShopException(message = "Product with id ${request.productId} not found", statusCode = HttpStatus.BAD_REQUEST)

        val orderPositionResponse = OrderPositionResponse(
                id = UUID.randomUUID().toString(),
                productId = request.productId,
                quantity = request.quantity

        )
        orderPositionRepository.save(orderPositionResponse)

        return orderPositionResponse;

    }

}