package com.investor.api.services

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.investor.api.API.AboutCoinAPI
import com.investor.api.API.ReturnJsonApi

import com.investor.api.dtos.HistoricCoinsDTO
import com.investor.api.dtos.InvestorDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.entities.Coin
import com.investor.api.entities.HistoricCoins
import com.investor.api.entities.Investor
import com.investor.api.projections.InvestorProjection
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
    private val coinAPI:AboutCoinAPI
) : InvestorProjection {

    override fun singInInvestor(investorDTO: InvestorDTO) {
        if (this.validateInvestor(investorDTO)) {

            val investor: Investor = Investor(
                name = investorDTO.name, email = investorDTO.email, password = investorDTO.password,
                coinMain = investorDTO.coinMain, coinSecond = investorDTO.coinSecond
            )

            var coinMainApi: ReturnJsonApi = this.testApiReturn(investorDTO.coinMain)
            var coinSecondApi: ReturnJsonApi = this.testApiReturn(investorDTO.coinSecond)

            val dateActual = Date(Calendar.getInstance().timeInMillis)

            var coinMain: Coin = Coin(name = investorDTO.coinMain, dateView = dateActual, coinMainApi.ask?.toDouble())
            var coinSecond: Coin = Coin(name = investorDTO.coinSecond, dateView = dateActual, coinSecondApi.ask?.toDouble())

            var coins: MutableList<Coin> = mutableListOf()
            coins.add(coinMain)
            coins.add(coinSecond)

            var historicCoins: HistoricCoins = HistoricCoins(investor = investor, coins = coins)

            // Salve o objeto HistoricCoins primeiro
            investor.historicCoins = historicCoins

            // Agora você pode salvar o Investor
            investorRepository.save(investor)
            historicCoinsRepository.save(historicCoins)

            // Associe o HistoricCoins ao Investor


            // Atualize manualmente o Investor com a referência à HistoricCoin

            // Resto do seu código
            // ...


        }

    }

fun findHistoric()=historicCoinsRepository.findAll()

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