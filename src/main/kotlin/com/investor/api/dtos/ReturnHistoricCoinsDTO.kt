package com.investor.api.dtos

import com.investor.api.entities.Coin
import jakarta.validation.constraints.NotNull

class ReturnHistoricCoinsDTO( @field:NotNull(message = "cannot empty") var coins:MutableList<Coin>? =null) {
}