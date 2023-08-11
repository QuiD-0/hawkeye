package com.quid.hawkeye.app.usecase

import com.quid.hawkeye.app.domain.Rate

interface App {
    fun initApp()
    fun getRateList(): List<Rate>
    fun closeApp()
}