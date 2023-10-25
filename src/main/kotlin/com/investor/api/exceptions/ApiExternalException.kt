package com.investor.api.exceptions

class ApiExternalException(override val message: String?) : RuntimeException(message) {
}