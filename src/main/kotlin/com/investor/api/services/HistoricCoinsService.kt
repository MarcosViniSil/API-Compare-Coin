package com.investor.api.services

import com.investor.api.API.AboutCoinAPI
import com.investor.api.API.ReturnJsonApi
import com.investor.api.dtos.HistoricCoinsDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.dtos.ReturnHistoricCoinsDTO
import com.investor.api.entities.Coin
import com.investor.api.entities.HistoricCoins
import com.investor.api.entities.Investor
import com.investor.api.exceptions.InvestorNotExistsException
import com.investor.api.exceptions.LoginInvestorException
import com.investor.api.projections.HistoricCoinsProjections
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
    private val coinRepository: CoinsRepository,
    private val investorService: InvestorService

) : HistoricCoinsProjections {

    override fun updateCoinsInvestor(investor: Investor) {
        var coinMainApi: ReturnJsonApi? = investorService.returnAPI(investor.coinMainName)
        var coinSecondApi: ReturnJsonApi? = investorService.returnAPI(investor.coinSecondName)
        if (coinMainApi != null && coinSecondApi != null) {
            investor.coinMainPrice = coinMainApi.ask?.toDouble()
            investor.coinSecondPrice = coinSecondApi.ask?.toDouble()

            this.updateHistoricInvestors(investor)
        }

    }

    override fun updateCoins() {
        var listInvestors: MutableList<Investor> = investorRepository.findAll()

        listInvestors.forEach { e -> updateCoinsInvestor(e) }


    }

    override fun updateHistoricInvestors(investor: Investor) {
        var historicCoinInvestorOptional = historicCoinsRepository.findById(investor.id!!)
        if (historicCoinInvestorOptional.isPresent()) {
            var historicCoinInvestor: HistoricCoins = historicCoinInvestorOptional.get()
            historicCoinInvestor.investor = investor

            var listCoinsInvestor: MutableList<Coin>? = historicCoinInvestor.coins
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
            historicCoinInvestor.coins = listCoinsInvestor


            investor.historicCoins = historicCoinInvestor
            investorRepository.save(investor)
        }
    }

    override fun listCoinsInvestor(loginInvestor: LoginInvestorDTO): ReturnHistoricCoinsDTO? {
        if (loginInvestor.email != null && loginInvestor.password != null) {
            var idInvestor: Long? = investorRepository.findIdInvestor(loginInvestor.email, loginInvestor.password)
            if (idInvestor != null) {
                var investorLogin: Investor = investorRepository.findById(idInvestor!!).get()
                var listCoinsInvestor: HistoricCoins? = investorLogin.historicCoins
                if (listCoinsInvestor?.coins != null) {
                    var historicCoinsDTO: ReturnHistoricCoinsDTO =
                        ReturnHistoricCoinsDTO(coins = listCoinsInvestor?.coins)
                    return historicCoinsDTO
                }


            } else {
                throw InvestorNotExistsException("investor not exists")
            }


        } else {
            throw LoginInvestorException("Investor invalid")
        }

        return null
    }

}