package com.investor.api.projections

import com.investor.api.dtos.HistoricCoinsDTO
import com.investor.api.dtos.InvestorDTO

interface HistricCoinsProjections {

    fun listHistoricCoins(investor: InvestorDTO): HistoricCoinsDTO
}