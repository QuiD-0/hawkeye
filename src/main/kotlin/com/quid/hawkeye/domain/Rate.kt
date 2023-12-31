package com.quid.hawkeye.domain

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

data class Rate(
    private val appName:String,
    private val country: String,
    private val remittanceType: String,
    private val sendAmount:BigDecimal,
    private val sendCurrency: String,
    private val receiveAmount:BigDecimal,
    private val receiveCurrency: String,
    private val rate: BigDecimal = sendAmount.divide(receiveAmount, 8, RoundingMode.DOWN),
    private val regDate: LocalDateTime = LocalDateTime.now(),
)
