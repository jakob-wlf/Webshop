package de.firecreeper82.shop.service

import de.firecreeper82.shop.exceptions.IdNotFoundException
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

        customerRepository.findById(request.customerId)

        return orderRepository.save(request)
    }

    fun createNewPositionForOrder(orderId: String, request: OrderPositionCreateRequest): OrderPositionResponse {

        orderRepository.findById(orderId)

        if (productRepository.findById(request.productId).isEmpty)
            throw IdNotFoundException(message = "Product with id ${request.productId} not found", statusCode = HttpStatus.BAD_REQUEST)

        val orderPositionResponse = OrderPositionResponse(
                id = UUID.randomUUID().toString(),
                orderId = orderId,
                productId = request.productId,
                quantity = request.quantity
        )
        orderPositionRepository.save(orderPositionResponse)

        return orderPositionResponse;

    }

}