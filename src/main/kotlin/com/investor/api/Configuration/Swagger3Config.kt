package com.investor.api.Configuration

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Swagger3Config {
    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("springcomaprecoinsapi-public")
            .pathsToMatch("/coins/historic/**", "/emails/historic/**","/investor/sigIn/**","/investor/delete/**")
            .build()
    }
}