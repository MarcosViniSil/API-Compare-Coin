package com.investor.api.services

import com.investor.api.repositories.HstoricEmailsRepository
import com.investor.api.repositories.InvestorRepository

class EmailService (private val investorRepository: InvestorRepository,
                    private val emailRepository: HstoricEmailsRepository
) {

    
}