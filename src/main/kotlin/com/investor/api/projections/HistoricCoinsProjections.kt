package com.investor.api.projections

import com.investor.api.dtos.HistoricCoinsDTO
import com.investor.api.dtos.InvestorDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.dtos.ReturnHistoricCoinsDTO
import com.investor.api.entities.Investor

interface HistoricCoinsProjections {

    fun updateCoinsInvestor(investor: Investor)

    fun updateCoins()

    fun updateHistoricInvestors(investor: Investor)

    fun listCoinsInvestor(loginInvestor: LoginInvestorDTO): ReturnHistoricCoinsDTO?
}