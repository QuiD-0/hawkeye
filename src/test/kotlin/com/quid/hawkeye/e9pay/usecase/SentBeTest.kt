package com.quid.hawkeye.e9pay.usecase

import com.quid.hawkeye.config.AppiumConfig
import com.quid.hawkeye.domain.AppInfo
import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.gateway.appium.SentBeDriver.SentBeAppiumDriver
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class SentBeTest {

    @Test
    fun sentBeAppTest() {
        val config = AppiumConfig(PhoneType.GALAXY_S10E, AppInfo.SENTBE)
        val wait = WebDriverWait(config.driver(), Duration.ofSeconds(10))
        val driver = SentBeAppiumDriver(wait).apply {
            allowPermission()
            selectLanguage()
            selectCountry()
        }
//        config.close()
    }
}