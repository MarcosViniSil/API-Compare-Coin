package com.investor.api.services

import com.investor.api.API.AboutCoinAPI
import com.investor.api.API.ReturnJsonApi
import com.investor.api.entities.Coin
import com.investor.api.entities.HistoricCoins
import com.investor.api.entities.Investor
import com.investor.api.repositories.CoinsRepository
import com.investor.api.repositories.HistoricCoinsRepository
import com.investor.api.repositories.InvestorRepository
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*
@Service
class HistoricCoinsService(
    private val investorRepository: InvestorRepository,
    private val historicCoinsRepository: HistoricCoinsRepository,
    private val coinAPI: AboutCoinAPI,
    private val coinRepository: CoinsRepository,
    private val investorService: InvestorService
) {

    fun updateCoinsInvestor(investor: Investor){
        var coinMainApi: ReturnJsonApi = investorService.returnAPI(investor.coinMainName)
        var coinSecondApi: ReturnJsonApi = investorService.returnAPI(investor.coinSecondName)

        investor.coinMainName=coinMainApi.name!!
        investor.coinMainPrice=coinMainApi.ask?.toDouble()

        investor.coinSecondName=coinSecondApi.name!!
        investor.coinSecondPrice=coinSecondApi.ask?.toDouble()

        this.updateHistoricInvestors(investor)
    }

    fun updateCoins(){
        var listInvestors:MutableList<Investor> = investorRepository.findAll()

        listInvestors.forEach { e -> updateCoinsInvestor(e) }


    }

    fun updateHistoricInvestors(investor: Investor){
        var historicCoinInvestor:HistoricCoins = historicCoinsRepository.findById(investor.id!!).get()

        historicCoinInvestor.investor=investor

        var listCoinsInvestor:MutableList<Coin>? = historicCoinInvestor.coins
        val coinMain = Coin(
            name = investor.coinMainName,
            dateView = Date(Calendar.getInstance().timeInMillis),
            value = investor.coinMainPrice,
            historic = historicCoinInvestor
        )
        val coinSecond = Coin(
            name = investor.coinSecondName,
            dateView = Date(Calendar.getInstance().timeInMillis),
            value = investor.coinSecondPrice,
            historic = historicCoinInvestor
        )
        coinRepository.save(coinMain)
        coinRepository.save(coinSecond)

        listCoinsInvestor?.add(coinMain)
        listCoinsInvestor?.add(coinSecond)
        historicCoinInvestor.coins=listCoinsInvestor


        investor.historicCoins = historicCoinInvestor
        investorRepository.save(investor)
    }

}