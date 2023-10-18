package com.investor.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ApiToCompareCoinsApplication

fun main(args: Array<String>) {

	runApplication<ApiToCompareCoinsApplication>(*args)
}
