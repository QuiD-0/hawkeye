package com.quid.hawkeye.usecase

import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate
import com.quid.hawkeye.gateway.e9pay.E9PayDriver
import org.springframework.stereotype.Component

@Component
class E9PayApp(
    private val driver: E9PayDriver,
) : App {
    override fun initApp(phone: PhoneType): App =
        with(driver) {
            init(phone)
            selectLanguage()
            permissionAllow()
        }.let { this }

    override fun getRateList(): List<Rate> =
        driver.gotoRatePage()
            .let { driver.getRateList() }

    override fun closeApp() = driver.close()

}