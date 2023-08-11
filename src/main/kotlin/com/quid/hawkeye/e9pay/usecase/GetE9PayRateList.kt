package com.quid.hawkeye.e9pay.usecase

import com.quid.hawkeye.app.PhoneType.GALAXY_A30
import com.quid.hawkeye.crawler.GetRateList
import com.quid.hawkeye.e9pay.domain.E9Pay
import com.quid.hawkeye.e9pay.gateway.appium.E9PayApp
import org.springframework.stereotype.Service

@Service
class GetE9PayRateList : GetRateList {
    override fun execute(): List<E9Pay> = with(E9PayApp(GALAXY_A30)) {
        initApp()
        getRateList()
            .also { closeApp() }
    }
}