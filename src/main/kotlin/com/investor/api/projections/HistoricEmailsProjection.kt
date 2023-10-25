package com.investor.api.projections

import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.dtos.ReturnHistoricEmailsDTO
import com.investor.api.entities.Investor

interface HistoricEmailsProjection {

    fun sendEmail(dataInvestor: Investor): Boolean

    fun sendEmailInvestors()

    fun listEmailsInvestor(loginInvestor: LoginInvestorDTO): ReturnHistoricEmailsDTO?
}