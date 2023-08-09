package de.firecreeper82.shop.repository

import de.firecreeper82.shop.model.OrderPositionResponse
import org.springframework.stereotype.Service

@Service
class OrderPositionRepository {

    val orderPositions = mutableListOf<OrderPositionResponse>();

    fun save(orderPositionResponse: OrderPositionResponse) {
        orderPositions.add(orderPositionResponse)
    }
}
