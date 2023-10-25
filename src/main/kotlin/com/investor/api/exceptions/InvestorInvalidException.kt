package com.investor.api.exceptions

class InvestorInvalidException(override val message: String?) : RuntimeException(message) {
}