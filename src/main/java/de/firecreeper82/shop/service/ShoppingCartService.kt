package de.firecreeper82.shop.service

import de.firecreeper82.shop.exceptions.IdNotFoundException
import de.firecreeper82.shop.model.OrderPositionResponse
import de.firecreeper82.shop.model.OrderResponse
import de.firecreeper82.shop.model.ProductResponse
import de.firecreeper82.shop.model.ShoppingCartResponse
import de.firecreeper82.shop.repository.CustomerRepository
import de.firecreeper82.shop.repository.OrderPositionRepository
import de.firecreeper82.shop.repository.OrderRepository
import de.firecreeper82.shop.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ShoppingCartService(val orderRepository: OrderRepository,
                          val orderPositionRepository: OrderPositionRepository,
                          val productRepository: ProductRepository,
                          val customerRepository: CustomerRepository
) {
    fun getShoppingCartForCustomer(customerId: String): ShoppingCartResponse {
        customerRepository.findById(customerId);


        val orders: List<OrderResponse> = orderRepository.findAllByCustomerIdWhereOrderStatusIsNew(customerId)

        val orderIds = orders.map { it.id }

        val orderPositions: List<OrderPositionResponse> = orderPositionRepository.findAllByOrderIds(orderIds)

        val deliveryCost = 800L
        val totalAmount = calculateSumForCart(orderPositions, deliveryCost)


        return ShoppingCartResponse(
            customerId = customerId,
            orderPositions = orderPositions,
            totalAmountInCent = totalAmount,
            deliveryCostInCent = deliveryCost,
            deliveryOption = "STANDARD"
        )
    }

    private fun calculateSumForCart(orderPositions: List<OrderPositionResponse>, deliveryCost: Long): Long {
        val positionAmounts: List<Long> = orderPositions.map {
            val product: ProductResponse = productRepository
                .findById(it.productId)
                .orElseThrow { IdNotFoundException("Order with id ${it.productId} not found") }
            it.quantity * product.priceInCent
        }
        val positionSum = positionAmounts.sum()

        return positionSum + deliveryCost
    }
}
