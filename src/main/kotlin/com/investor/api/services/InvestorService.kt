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
                coinMainName = investorDTO.coinMain, coinSecondName = investorDTO.coinSecond
            )

            var coinMainApi: ReturnJsonApi = this.returnAPI(investorDTO.coinMain)
            var coinSecondApi: ReturnJsonApi = this.returnAPI(investorDTO.coinSecond)

            val dateActual = Date(Calendar.getInstance().timeInMillis)

            var coinMain: Coin = Coin(name = investorDTO.coinMain, dateView = dateActual, coinMainApi.ask?.toDouble())
            var coinSecond: Coin = Coin(name = investorDTO.coinSecond, dateView = dateActual, coinSecondApi.ask?.toDouble())

            var nameC: MutableList<String> = mutableListOf()
            nameC.add(coinMain.name)
            nameC.add(coinSecond.name)

            var dateViewC: MutableList<Date> = mutableListOf()
            dateViewC.add(dateActual)
            dateViewC.add(dateActual)

            var valueC: MutableList<Double?> = mutableListOf()
            valueC.add(coinMain.value?.toDouble())
            valueC.add(coinSecond.value?.toDouble())

            var historicCoins: HistoricCoins = HistoricCoins(investor = investor,name=nameC,dateView=dateViewC,value=valueC )

            investor.historicCoins = historicCoins
            investor.coinMainPrice=coinMainApi.ask?.toDouble()
            investor.coinSecondPrice=coinSecondApi.ask?.toDouble()
            investorRepository.save(investor)
            historicCoinsRepository.save(historicCoins)


        }

    }

fun findHistoric()=investorRepository.findById(1L)

    override fun loginInvestor(investor: LoginInvestorDTO): InvestorDTO {
        TODO("Not yet implemented")
    }

    override fun InsertCoins(investor: Investor): Boolean {
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

    fun returnAPI(currency: String):ReturnJsonApi {
        println(currency)
        val stringRemove:String=currency+"BRL"
        val inputJson:String =coinAPI.getCurrency(currency)
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