package de.firecreeper82.shop.exceptions

import org.springframework.http.HttpStatus

data class WebShopException(
    override val message: String,
    val statusCode: HttpStatus
): Exception(message)