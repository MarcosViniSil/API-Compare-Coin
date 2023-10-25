package com.investor.api.projections

import com.investor.api.dtos.HistoricCoinsDTO
import com.investor.api.dtos.HistoricEmailsDTO
import com.investor.api.dtos.InvestorDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.entities.Coin
import com.investor.api.entities.HistoricCoins
import com.investor.api.entities.Investor

interface InvestorProjection {

    fun singInInvestor(investor: InvestorDTO)

    fun deleteInvestor(loginInvestor: LoginInvestorDTO)




}