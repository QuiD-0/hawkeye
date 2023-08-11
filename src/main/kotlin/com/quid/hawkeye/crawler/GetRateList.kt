package com.quid.hawkeye.crawler

import com.quid.hawkeye.app.Rate

interface GetRateList {

    fun execute(): List<Rate>

}