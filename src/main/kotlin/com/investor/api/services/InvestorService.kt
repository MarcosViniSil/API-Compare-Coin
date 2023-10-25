package com.investor.api.services

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.investor.api.API.AboutCoinAPI
import com.investor.api.API.ReturnJsonApi

import com.investor.api.dtos.HistoricCoinsDTO
import com.investor.api.dtos.InvestorDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.dtos.ReturnHistoricEmailsDTO
import com.investor.api.entities.Coin
import com.investor.api.entities.HistoricCoins
import com.investor.api.entities.HistoricEmails
import com.investor.api.entities.Investor
import com.investor.api.projections.InvestorProjection
import com.investor.api.repositories.CoinsRepository
import com.investor.api.repositories.HistoricCoinsRepository
import com.investor.api.repositories.InvestorRepository
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*
import java.util.regex.Pattern


@Service
class InvestorService(
    private val investorRepository: InvestorRepository,
    private val historicCoinsRepository: HistoricCoinsRepository,
    private val coinAPI: AboutCoinAPI,
    private val coinRepository: CoinsRepository
) : InvestorProjection {

    override fun singInInvestor(investorDTO: InvestorDTO) {
        if (this.validateInvestor(investorDTO)) {

            val investor: Investor = Investor(
                name = investorDTO.name, email = investorDTO.email, password = investorDTO.password,
                coinMainName = investorDTO.coinMain, coinSecondName = investorDTO.coinSecond
            )


            var coinMainApi: ReturnJsonApi = this.returnAPI(investorDTO.coinMain)
            var coinSecondApi: ReturnJsonApi = this.returnAPI(investorDTO.coinSecond)

            val dateActual = Date(Calendar.getInstance().timeInMillis)

            val historicCoins = HistoricCoins()
            historicCoins.investor = investor

            historicCoinsRepository.save(historicCoins)
            investorRepository.save(investor)
            val coinMain = Coin(
                name = investorDTO.coinMain,
                dateView = dateActual,
                value = coinMainApi.ask?.toDouble(),
                historic = historicCoins
            )
            val coinSecond = Coin(
                name = investorDTO.coinSecond,
                dateView = dateActual,
                value = coinSecondApi.ask?.toDouble(),
                historic = historicCoins
            )
            coinRepository.save(coinMain)
            coinRepository.save(coinSecond)

            historicCoins.coins = mutableListOf(coinMain, coinSecond)
            historicCoinsRepository.save(historicCoins)

            investor.coinMainPrice = coinMainApi.ask?.toDouble()
            investor.coinSecondPrice = coinSecondApi.ask?.toDouble()

            investor.historicCoins = historicCoins
            investorRepository.save(investor)


        }

    }

    override fun deleteInvestor(loginInvestor: LoginInvestorDTO) {
        if (loginInvestor.email != null && loginInvestor.password != null) {
            var idInvestor: Long? = investorRepository.findIdInvestor(loginInvestor.email, loginInvestor.password)
            if (idInvestor != null) {

                investorRepository.deleteById(idInvestor)


            } else {
                //TODO exception Investor not exists
            }


        } else {
            //TODO exception loginInvestor invalid
        }


    }


    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun validateInvestor(investor: InvestorDTO): Boolean {
        if (investor != null) {
            if (investor.email != null && investor.name != null && investor.coinMain != null && investor.coinSecond != null && investor.password != null) {
                return true
            }
        }
        return false

    }

    fun returnAPI(currency: String): ReturnJsonApi {
        println(currency)
        val stringRemove: String = currency + "BRL"
        val inputJson: String = coinAPI.getCurrency(currency)
        val startIndex = inputJson.indexOf('{')
        val endIndex = inputJson.lastIndexOf('}')

        val jsonPart = inputJson.substring(startIndex + 1, endIndex)
        val jsonWithoutCoin = jsonPart.replace("\"$stringRemove\":", "")

        val objectMapper: ObjectMapper = ObjectMapper()
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        val currency: ReturnJsonApi = objectMapper.readValue(jsonWithoutCoin, ReturnJsonApi::class.java)
        return currency


    }
}