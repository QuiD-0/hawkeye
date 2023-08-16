package com.quid.hawkeye.usecase

import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate

interface App {
    fun getRateList(): List<Rate>
    fun use(phone: PhoneType, block: (App) -> Unit)
}