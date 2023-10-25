package com.investor.api.exceptions

data class InvestorNullException(override val message: String?) : RuntimeException(message) {
}