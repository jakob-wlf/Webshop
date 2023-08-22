package de.firecreeper82.shop.repository

import de.firecreeper82.shop.entity.OrderEntity
import de.firecreeper82.shop.model.OrderStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime
import java.util.UUID

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    lateinit var repository: OrderRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setupTestData() {
        customerRepository.save(
            CustomerEntity(
                id = "234",
                firstName = "",
                lastName = "",
                salutation = "",
                email = ""
            )
        )
        customerRepository.save(
            CustomerEntity(
                id = "2343",
                firstName = "",
                lastName = "",
                salutation = "",
                email = ""
            )
        )
    }

    @Test
    fun testThatFindAllOnEmptyDbReturnsEmptyList() {
        //when
        val result = repository.findAll()

        //then
        assertThat(result).isEmpty()
    }

    @Test
    fun testThatFindAllAfterSavingOrderReturnsSavedOrder() {
        //given
        val orderEntity = OrderEntity(
            id = "1",
            customerId = "234",
            orderTime = LocalDateTime.now(),
            orderStatus = OrderStatus.NEW,
            orderPositions = emptyList()
        )
        repository.save(
            orderEntity
        )

        //when
        val result = repository.getReferenceById("1")

        //then
        assertThat(result).usingRecursiveComparison().ignoringFields("orderPositions").isEqualTo(orderEntity)
        assertThat(result.orderPositions).isEmpty()
    }

    @Test
    fun testFindAllByCustomerIdWhereOrderStatusIsNewReturnsExpectedOrder() {
        //given
        val orderEntity = createOrderEntity("234", OrderStatus.NEW)
        repository.save(orderEntity)
        repository.save(createOrderEntity("234", OrderStatus.CANCELED))
        repository.save(createOrderEntity("2343", OrderStatus.NEW))

        //when
        val result = repository.findAllByCustomerIdWhereOrderStatusIsNew("234")

        //then
        val firstResult = repository.findAllByCustomerIdWhereOrderStatusIsNew("234")[0]
        assertThat(result.size).isEqualTo(1)
        assertThat(firstResult).usingRecursiveComparison().ignoringFields("orderPositions").isEqualTo(orderEntity)
    }

    private fun createOrderEntity(customerId: String, status: OrderStatus) = OrderEntity(
        id = UUID.randomUUID().toString(),
        customerId = customerId,
        orderTime = LocalDateTime.now(),
        orderStatus = status,
        orderPositions = emptyList()
    )
}