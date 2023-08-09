package de.firecreeper82.shop.repository

import de.firecreeper82.shop.model.OrderPositionResponse

class OrderPositionRepository {

    val orderPositions = mutableListOf<OrderPositionResponse>();

    fun save(orderPositionResponse: OrderPositionResponse) {
        orderPositions.add(orderPositionResponse)
    }
}
