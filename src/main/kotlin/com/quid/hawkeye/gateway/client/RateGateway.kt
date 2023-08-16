package com.quid.hawkeye.gateway.client

import com.quid.hawkeye.domain.Rate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

interface RateGateway {
    fun save(data: List<Rate>)

    @Component
    class RateWebClientGateway(
        private val rateWebClient: WebClient
    ) : RateGateway {
        override fun save(data: List<Rate>) {
            println(data)
        }
    }
}