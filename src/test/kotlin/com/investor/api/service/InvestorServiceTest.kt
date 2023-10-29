package com.investor.api.service

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import com.investor.api.API.AboutCoinAPI
import com.investor.api.dtos.InvestorDTO
import com.investor.api.repositories.CoinsRepository
import com.investor.api.repositories.HistoricCoinsRepository
import com.investor.api.repositories.InvestorRepository
import com.investor.api.services.EmailService
import com.investor.api.services.InvestorService
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class InvestorServiceTest {
    @MockK lateinit var coinsRepository :CoinsRepository
    @MockK lateinit var historicCoinsRepository : HistoricCoinsRepository
    @MockK lateinit var investorRepository :InvestorRepository
    @MockK lateinit var coinAPI :AboutCoinAPI
    @MockK lateinit var coinRepository :CoinsRepository

    @InjectMockKs lateinit var investorService :InvestorService
    @InjectMockKs
    lateinit var historicEmailsService : EmailService

    @Test
    fun `should return true because email is valid`(){
        var email:String="testEmail@gmail.com"
        Assertions.assertEquals(investorService.isEmailValid(email),true)
    }

    @Test
    fun `should return false because email not is valid`(){
        var email:String="testEmailgmail.com"
        Assertions.assertEquals(investorService.isEmailValid(email),false)
    }

    @Test
    fun `should return true because coin is valid`(){
        var coin:String="USD"
        Assertions.assertEquals(investorService.validateCoinsInvestor(coin),true)
    }

    @Test
    fun `should return false because coin is not valid`(){
        var coin:String="USDr"
        Assertions.assertEquals(investorService.validateCoinsInvestor(coin),false)
    }




}


