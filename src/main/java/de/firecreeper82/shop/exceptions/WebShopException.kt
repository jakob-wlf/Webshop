package de.firecreeper82.shop.exceptions

import org.springframework.http.HttpStatus

class WebShopException(
    override val message: String,
    val statusCode: HttpStatus
): Exception(message)

class IdNotFoundException(
    override val message: String,
    val statusCode: HttpStatus = HttpStatus.BAD_REQUEST
): Exception(message)