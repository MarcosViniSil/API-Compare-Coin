package com.investor.api.dtos

import com.investor.api.entities.Coin
import jakarta.persistence.ElementCollection

class HistoricCoinsDTO(@ElementCollection var coins: List<Coin> = mutableListOf()) {
}