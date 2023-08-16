package de.firecreeper82.shop.repository

import de.firecreeper82.shop.exceptions.IdNotFoundException
import de.firecreeper82.shop.model.OrderCreateRequest
import de.firecreeper82.shop.model.OrderPositionResponse
import de.firecreeper82.shop.model.OrderResponse
import de.firecreeper82.shop.model.OrderStatus
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

//@Service
//class OrderRepository {
//
//    val orders = mutableListOf<OrderResponse>();
//
//    fun save(response: OrderResponse): OrderResponse {
//        orders.add(response)
//        return response
//    }
//
//    fun findById(orderId: String): OrderResponse {
//        return orders.find { it.id == orderId }
//            ?: throw IdNotFoundException(message = "Order with id $orderId not found", statusCode = HttpStatus.BAD_REQUEST)
//    }
//
//    fun findAllByCustomerIdWhereOrderStatusIsNew(customerId: String): List<OrderResponse> {
//        return orders.filter { it.customerId == customerId && it.orderStatus == OrderStatus.NEW }
//    }
//}


interface OrderRepository: JpaRepository<OrderEntity, String> {

    @Query("SELECT e FROM OrderEntity e WHERE e.orderStatus = 'NEW' AND e.customerId = :customerId")
    fun findAllByCustomerIdWhereOrderStatusIsNew(customerId: String): List<OrderEntity>
}

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id val id: String,
    val customerId: String,
    val orderTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus,
)