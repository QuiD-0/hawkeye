package com.quid.hawkeye.e9pay.usecase

import com.quid.hawkeye.app.domain.PhoneType
import com.quid.hawkeye.app.gateway.appium.e9pay.E9PayApp
import org.junit.jupiter.api.Test


class GetRateListTest {

    @Test
    fun `test execute`() {
        val data = with(E9PayApp(PhoneType.GALAXY_A30)) {
            initApp()
            getRateList()
                .also { closeApp() }
        }
        println(data)
    }

}