package com.investor.api.controller

import com.investor.api.API.ReturnJsonApi
import com.investor.api.services.InvestorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class InvestorController(private val investorservice:InvestorService) {
    @GetMapping("/coin/{Coin}")
    fun getCoin(@PathVariable("Coin") Coin:String ): ReturnJsonApi {
       return investorservice.testApiReturn(Coin)
    }

}