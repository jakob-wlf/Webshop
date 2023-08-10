package de.firecreeper82.shop.controller

import de.firecreeper82.shop.model.CustomerResponse
import de.firecreeper82.shop.model.ShoppingCartResponse
import de.firecreeper82.shop.repository.CustomerRepository
import de.firecreeper82.shop.service.ShoppingCartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(val customerRepository: CustomerRepository, val shoppingCartService: ShoppingCartService) {

    @GetMapping("/customers")
    fun getAllCustomers(): List<CustomerResponse> {
        return customerRepository.customers;
    }

    @GetMapping("/customers/{id}")
    fun getCustomerById(@PathVariable id: String): CustomerResponse {

        val customer = customerRepository.findById(id);
        return customer
    }

    @GetMapping("/customers/{id}/shoppingcart")
    fun getShoppingCartByCustomerId(@PathVariable id: String): ShoppingCartResponse {
        return shoppingCartService.getShoppingCartForCustomer(id)
    }
}