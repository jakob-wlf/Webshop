package de.firecreeper82.shop.repository

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
interface ProductRepository : JpaRepository<ProductEntity, String>

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id val id: String,
    val name: String,
    val description: String,
    val priceInCent: Int,
    @ElementCollection val tags: List<String>
)