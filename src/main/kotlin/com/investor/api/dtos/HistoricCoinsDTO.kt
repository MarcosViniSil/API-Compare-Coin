package com.investor.api.dtos

import com.investor.api.entities.Coin
import com.investor.api.entities.HistoricCoins
import com.investor.api.entities.Investor
import jakarta.persistence.ElementCollection

class HistoricCoinsDTO(val investor: Investor, @ElementCollection var coins: MutableList<Coin> = mutableListOf())


{
    fun toEntity():HistoricCoins= HistoricCoins(investor=this.investor, coins = this.coins)
}