package com.quid.hawkeye.app.gateway.appium.e9pay

import com.quid.hawkeye.app.domain.PhoneType
import com.quid.hawkeye.app.domain.Rate
import com.quid.hawkeye.app.usecase.GetRateList
import org.springframework.stereotype.Service

@Service
class GetE9PayRateList : GetRateList {
    override fun execute(phone: PhoneType): List<Rate> = with(E9PayApp(phone)) {
        initApp()
        getRateList()
            .also { closeApp() }
    }
}