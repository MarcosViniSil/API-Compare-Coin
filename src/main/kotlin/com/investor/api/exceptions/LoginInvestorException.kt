package com.investor.api.exceptions

class LoginInvestorException(override val message: String?) : RuntimeException(message) {
}