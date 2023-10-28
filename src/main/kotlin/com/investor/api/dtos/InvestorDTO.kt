package com.investor.api.dtos

import jakarta.validation.constraints.*

class InvestorDTO(
    @field:NotNull(message = "the field name cannot empty") var name: String = "",
    @field:Email(message = "Invalid email") var email: String = "",
    @field:NotNull(message = "the field password cannot empty") var password: String = "",
    @field:NotNull(message = "the field coin main cannot empty") var coinMain: String = "",
    @field:NotNull(message = "the field coin second cannot empty") var coinSecond: String = ""
) {
}