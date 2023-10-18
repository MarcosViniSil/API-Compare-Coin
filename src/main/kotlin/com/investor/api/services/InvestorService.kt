package com.investor.api.services

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.investor.api.API.AboutCoinAPI
import com.investor.api.API.ReturnJsonApi

import com.investor.api.dtos.HistoricCoinsDTO
import com.investor.api.dtos.InvestorDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.entities.Investor
import com.investor.api.projections.InvestorProjection
import com.investor.api.repositories.HistoricCoinsRepository
import com.investor.api.repositories.InvestorRepository
import org.springframework.stereotype.Service
import java.util.regex.Pattern


@Service
class InvestorService(
    private val investorRepository: InvestorRepository,
    private val historicCoinsRepository: HistoricCoinsRepository,
    private val coinAPI:AboutCoinAPI
) : InvestorProjection {

    override fun singInInvestor(investor: InvestorDTO) {
        if (this.validateInvestor(investor)) {

                val investor: Investor = Investor(
                    name = investor.name, email = investor.email, password = investor.password,
                    coinMain = investor.coinMain, coinSecond = investor.coinSecond
                )

                investorRepository.save(investor)

        }
    }

    override fun loginInvestor(investor: LoginInvestorDTO): InvestorDTO {
        TODO("Not yet implemented")
    }

    override fun InsertCoins(investor: Investor): Boolean {

        var historicEmails: HistoricCoinsDTO=HistoricCoinsDTO(investor=investor,)
        return false;
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
    private fun validateInvestor(investor: InvestorDTO):Boolean{
        if(investor!=null){
            if(investor.email!=null && investor.name!=null && investor.coinMain!=null && investor.coinSecond!=null && investor.password!=null){
                return true
            }
        }
        return false

    }

    fun testApiReturn(currency: String):ReturnJsonApi {
        println(currency)
        val stringRemove:String=currency+"BRL"
        val inputJson:String =coinAPI.getCurrency(currency)
        val startIndex = inputJson.indexOf('{')
        val endIndex = inputJson.lastIndexOf('}')

            val jsonPart = inputJson.substring(startIndex + 1, endIndex)
            val jsonWithoutCoin = jsonPart.replace("\"$stringRemove\":", "")

            val objectMapper: ObjectMapper = ObjectMapper()
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL) // Ignora campos nulos
            val currency: ReturnJsonApi = objectMapper.readValue(jsonWithoutCoin, ReturnJsonApi::class.java)
            return currency




    }
}