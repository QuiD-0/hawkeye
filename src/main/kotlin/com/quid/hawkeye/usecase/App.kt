package com.quid.hawkeye.usecase

import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate

interface App {
    fun initApp(phone: PhoneType)
    fun getRateList(): List<Rate>
    fun closeApp()
}