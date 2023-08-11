package com.quid.hawkeye.usecase

import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate
import com.quid.hawkeye.gateway.e9pay.E9PayDriver
import org.springframework.stereotype.Component

@Component
class E9PayApp(
    private val e9payDriver: E9PayDriver,
) : App {
    override fun initApp(phone: PhoneType) =
        with(e9payDriver) {
            init(phone)
            selectLanguage()
            permissionAllow()
        }

    override fun getRateList(): List<Rate> =
        e9payDriver.gotoRatePage()
            .let { e9payDriver.getRateList() }

    override fun closeApp() = e9payDriver.close()

}