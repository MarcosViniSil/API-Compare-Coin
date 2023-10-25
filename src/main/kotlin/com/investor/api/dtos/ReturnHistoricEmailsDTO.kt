package com.investor.api.dtos

import com.investor.api.entities.Email
import jakarta.validation.constraints.NotNull

class ReturnHistoricEmailsDTO(@field:NotNull(message = "cannot empty")  var emails:MutableList<Email>? =null) {
}