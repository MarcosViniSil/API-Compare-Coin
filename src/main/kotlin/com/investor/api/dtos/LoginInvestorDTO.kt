package com.investor.api.dtos

import jakarta.validation.constraints.*

class LoginInvestorDTO(@field:Email(message = "Invalid email") var email:String="",
                       @field:NotNull(message = "the field password cannot empty") var password: String = "") {
}