package com.quid.hawkeye.e9pay.usecase

import com.quid.hawkeye.config.AppiumConfig
import com.quid.hawkeye.domain.AppInfo
import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.gateway.appium.E9PayDriver.*
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class GetRateListTest {

    @Test
    fun `test execute`() {
        val config = AppiumConfig(PhoneType.GALAXY_A30, AppInfo.E9PAY)
        val wait = WebDriverWait(config.driver(), Duration.ofSeconds(10))
        val driver = E9PayAppiumDriver(wait).apply {
            selectLanguage()
            permissionAllow()
            gotoRatePage()
        }
        val rateList = driver.getRateList()
        println(rateList)
        config.close()
    }

}