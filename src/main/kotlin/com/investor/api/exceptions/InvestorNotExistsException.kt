package com.investor.api.exceptions

class InvestorNotExistsException(override val message: String?) : RuntimeException(message)  {
}