package de.firecreeper82.shop.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    lateinit var repository: CustomerRepository

    @Test
    fun testThatFindAllOnEmptyDbReturnsEmptyList() {

        //when
        val result: List<CustomerEntity> = repository.findAll();

        //then
        assertThat(result).isEmpty()
    }
    @Test
    fun testThatFindAllOnDbAfterSavingCustomerReturnsListWithCustomers() {
        //given
        val customerEntity = CustomerEntity(
            id = "1",
            firstName = "Donald",
            lastName = "Duck",
            "Mr.",
            "donald@duck.de"
        )
        repository.save(
            customerEntity
        )


        //when
        val result: List<CustomerEntity> = repository.findAll();

        //then
        assertThat(result).containsExactly(customerEntity)

    }
}