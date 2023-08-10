package de.firecreeper82.shop.repository

import de.firecreeper82.shop.model.OrderPositionResponse
import org.springframework.stereotype.Service

@Service
class OrderPositionRepository {

    private val orderPositions = mutableListOf<OrderPositionResponse>();

    fun save(orderPositionResponse: OrderPositionResponse) {
        orderPositions.add(orderPositionResponse)
    }

    fun findAllByOrderIds(orderIds: List<String>): List<OrderPositionResponse> {
        TODO("Not yet implemented")
    }
}
