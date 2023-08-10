package de.firecreeper82.shop.repository

import de.firecreeper82.shop.exceptions.IdNotFoundException
import de.firecreeper82.shop.exceptions.WebShopException
import de.firecreeper82.shop.model.CustomerResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CustomerRepository {

    val customers = listOf(
            CustomerResponse("1", "Jakob", "Wolf", "jakobwolfwbg000@gmail.com")
    );
    fun findById(id: String): CustomerResponse {
        return customers.find { it.id == id }?: throw IdNotFoundException("Customer with id $id not found", HttpStatus.BAD_REQUEST)
    }
}