package com.investor.api.exceptions

class EmailInvalidException(override val message: String?) : RuntimeException(message) {
}