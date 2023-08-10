package de.firecreeper82.shop.service

import de.firecreeper82.shop.model.OrderPositionResponse
import de.firecreeper82.shop.model.OrderResponse
import de.firecreeper82.shop.model.ShoppingCartResponse
import de.firecreeper82.shop.repository.OrderPositionRepository
import de.firecreeper82.shop.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class ShoppingCartService(val orderRepository: OrderRepository,
                          val orderPositionRepository: OrderPositionRepository
) {
    fun getShoppingCartForCustomer(customerId: String): ShoppingCartResponse {

        val orders: List<OrderResponse> = orderRepository.findAllByCustomerIdWhereOrderStatusIsNew(customerId)

        val orderIds = orders.map { it.id }

        val orderPositions: List<OrderPositionResponse> = orderPositionRepository.findAllByOrderIds(orderIds)

        return ShoppingCartResponse(
            customerId = customerId,
            orderPositions = orderPositions,
            totalAmountInCent = 0,
            deliveryCostInCent = 0,
            deliveryOption = ""
        )

    }
}
