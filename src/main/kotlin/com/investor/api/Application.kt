package com.investor.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@OpenAPIDefinition(servers = [Server(url = "/", description = "Default Server URL")])
class ApiToCompareCoinsApplication

fun main(args: Array<String>) {

	runApplication<ApiToCompareCoinsApplication>(*args)
}
