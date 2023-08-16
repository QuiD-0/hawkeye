package com.quid.hawkeye.gateway.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class RateWebClientConfig {

    @Bean
    fun rateWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build()
    }
}