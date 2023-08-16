package de.firecreeper82.shop.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<CustomerEntity, String> {

}

@Entity
@Table(name = "customers")
data class CustomerEntity(
    @Id val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)