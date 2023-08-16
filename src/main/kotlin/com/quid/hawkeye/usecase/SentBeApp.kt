package com.quid.hawkeye.usecase

import com.quid.hawkeye.config.AppiumConfig
import com.quid.hawkeye.domain.AppInfo
import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate
import com.quid.hawkeye.gateway.appium.SentBeDriver
import com.quid.hawkeye.gateway.appium.SentBeDriver.SentBeAppiumDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component

@Component
class SentBeApp: App {
    private lateinit var config: AppiumConfig
    private lateinit var wait: WebDriverWait
    private lateinit var driver: SentBeDriver

    override fun getRateList(): List<Rate> {
        TODO("Not yet implemented")
    }

    override fun use(phone: PhoneType, block: (App) -> Unit) {
        initApp(phone)
        block(this)
        config.close()
    }

    private fun initApp(phone: PhoneType) {
        this.config = AppiumConfig(phone, AppInfo.SENTBE)
        this.wait = WebDriverWait(config.driver(), java.time.Duration.ofSeconds(10))
        this.driver = SentBeAppiumDriver(wait)
    }
}