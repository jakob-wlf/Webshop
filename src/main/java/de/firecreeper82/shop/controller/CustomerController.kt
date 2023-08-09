package de.firecreeper82.shop.controller

import de.firecreeper82.shop.model.CustomerResponse
import de.firecreeper82.shop.repository.CustomerRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(val customerRepository: CustomerRepository) {

    @GetMapping("/customers")
    fun getAllCustomers(): List<CustomerResponse> {
        return customerRepository.customers;
    }

    @GetMapping("/customers/{id}")
    fun getCustomerById(@PathVariable id: String): ResponseEntity<CustomerResponse> {

        val response = customerRepository.findById(id);
        return if(response != null)
            ResponseEntity.ok(response)
        else
            ResponseEntity.notFound().build()
    }
}