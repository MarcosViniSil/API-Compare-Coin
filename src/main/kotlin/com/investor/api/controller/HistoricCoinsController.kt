package com.investor.api.controller

import com.investor.api.dtos.InvestorDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.dtos.ReturnHistoricCoinsDTO
import com.investor.api.services.HistoricCoinsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coins")
class HistoricCoinsController(private val historicCoinsService: HistoricCoinsService) {

    @PostMapping("/historic")
    fun getCoin(@RequestBody investor: LoginInvestorDTO):ReturnHistoricCoinsDTO?{
        return historicCoinsService.listCoinsInvestor(investor)

    }

}