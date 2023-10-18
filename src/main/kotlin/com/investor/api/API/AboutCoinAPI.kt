package com.investor.api.API

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "awesomeapi", url = "https://economia.awesomeapi.com.br/last")
interface AboutCoinAPI {

    @GetMapping("/{currency}")
    fun getCurrency(@PathVariable("currency") currency:String ):String
}

