package com.quid.hawkeye.app.usecase

import com.quid.hawkeye.app.domain.PhoneType
import com.quid.hawkeye.app.domain.Rate

interface GetRateList {

    fun execute(phone: PhoneType): List<Rate>

}