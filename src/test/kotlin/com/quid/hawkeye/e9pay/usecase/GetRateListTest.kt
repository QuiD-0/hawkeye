package com.quid.hawkeye.e9pay.usecase

import com.quid.hawkeye.app.PhoneType
import com.quid.hawkeye.e9pay.gateway.appium.E9PayApp
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