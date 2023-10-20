package com.investor.api.controller

import com.investor.api.API.ReturnJsonApi
import com.investor.api.dtos.InvestorDTO
import com.investor.api.entities.Coin
import com.investor.api.entities.HistoricCoins
import com.investor.api.entities.Investor
import com.investor.api.services.InvestorService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class InvestorController(private val investorservice:InvestorService) {
    @PostMapping("/sigIn")
    fun getCoin(@RequestBody investor: InvestorDTO){
        investorservice.singInInvestor(investor)

    }

    @GetMapping("/u")
    fun teste(): Optional<Investor> {
        return investorservice.findHistoric()
    }

}