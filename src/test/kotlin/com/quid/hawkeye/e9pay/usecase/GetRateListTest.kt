package com.quid.hawkeye.e9pay.usecase

import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.gateway.e9pay.E9PayDriver
import com.quid.hawkeye.usecase.E9PayApp
import org.junit.jupiter.api.Test


class GetRateListTest {

    @Test
    fun `test execute`() {
        val data = with(E9PayApp(E9PayDriver.E9PayDriverImpl())) {
            initApp(PhoneType.GALAXY_A30)
            getRateList()
                .also { closeApp() }
        }
        println(data)
    }

}