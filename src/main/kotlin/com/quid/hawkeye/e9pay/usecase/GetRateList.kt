package com.quid.hawkeye.e9pay.usecase

interface GetRateList {

    fun execute(): List<Any>

    class GetRateListImpl : GetRateList {
        override fun execute(): List<Any> {
            return listOf("1", "2", "3")
        }
    }
}