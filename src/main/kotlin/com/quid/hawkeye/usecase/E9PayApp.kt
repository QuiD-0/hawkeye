package com.quid.hawkeye.usecase

import com.quid.hawkeye.config.AppiumConfig
import com.quid.hawkeye.domain.AppInfo
import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate
import com.quid.hawkeye.gateway.appium.E9PayDriver
import com.quid.hawkeye.gateway.appium.E9PayDriver.*
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class E9PayApp: App {
    private lateinit var config: AppiumConfig
    private lateinit var wait: WebDriverWait
    private lateinit var driver: E9PayDriver

    override fun getRateList(): List<Rate> {
        driver.gotoRatePage()
        return driver.getRateList()
    }

    override fun use(phone: PhoneType, block: (App) -> Unit) {
        initApp(phone)
        block(this)
        config.close()
    }

    private fun initApp(phone: PhoneType) {
        this.config = AppiumConfig(phone, AppInfo.E9PAY)
        this.wait = WebDriverWait(config.driver(), Duration.ofSeconds(10))
        this.driver = E9PayAppiumDriver(wait).apply {
            selectLanguage()
            allowPermission()
        }
    }

}