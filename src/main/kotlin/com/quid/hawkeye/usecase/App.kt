package com.quid.hawkeye.usecase

import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate

interface App {
    fun driver(phone: PhoneType): App
    fun initApp()
    fun getRateList(): List<Rate>
    fun closeApp()
}