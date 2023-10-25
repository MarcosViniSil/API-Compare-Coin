package com.investor.api.exceptions

class CoinInvalidException(override val message: String?) : RuntimeException(message) {
}