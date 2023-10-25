package com.investor.api.controller

import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.dtos.ReturnHistoricCoinsDTO
import com.investor.api.dtos.ReturnHistoricEmailsDTO
import com.investor.api.services.EmailService
import com.investor.api.services.HistoricCoinsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/emails")
class HistoricEmailsController(private val historicEmailsService: EmailService) {

    @PostMapping("/historic")
    fun getCoin(@RequestBody investor: LoginInvestorDTO): ReturnHistoricEmailsDTO?{
        return historicEmailsService.listEmailsInvestor(investor)

    }
}