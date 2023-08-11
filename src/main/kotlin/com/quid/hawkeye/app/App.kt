package com.quid.hawkeye.app

interface App {
    fun initApp()
    fun getRateList(): List<Rate>
    fun closeApp()
}