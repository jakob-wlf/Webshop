package de.firecreeper82.shop.repository

import de.firecreeper82.shop.model.CustomerResponse
import java.util.UUID

class CustomerRepository {

    val customers = listOf(
            CustomerResponse("1", "Jakob", "Wolf", "jakobwolfwbg000@gmail.com")
    );
    fun findById(id: String): CustomerResponse? {
        return customers.find { it.id == id }
    }
}