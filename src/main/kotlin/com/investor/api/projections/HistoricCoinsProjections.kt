package com.investor.api.projections

import com.investor.api.dtos.HistoricEmailsDTO
import com.investor.api.dtos.InvestorDTO

interface HistoricCoinsProjections {

    fun sendEmailUsers():Boolean

    fun listHistoricEmails(investor: InvestorDTO): HistoricEmailsDTO
}