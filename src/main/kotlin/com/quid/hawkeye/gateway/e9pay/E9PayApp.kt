package com.quid.hawkeye.gateway.e9pay

import com.quid.hawkeye.usecase.App
import com.quid.hawkeye.domain.AppInfo.E9PAY
import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate
import com.quid.hawkeye.config.AppiumConfig
import io.appium.java_client.AppiumBy
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class E9PayApp: App {
    private lateinit var config: AppiumConfig
    private lateinit var driver: WebDriverWait

    override fun driver(phone: PhoneType): App {
        this.config = AppiumConfig(phone, E9PAY)
        this.driver = WebDriverWait(config.driver(), Duration.ofSeconds(10))
        return this
    }

    override fun initApp() {
        driver.also {
            selectLanguage()
            permissionAllow()
            gotoRatePage()
        }
    }

    override fun getRateList(): List<Rate> {
        val text = driver.until { it.findElement(AppiumBy.id(RATE)).text }
        return mutableListOf()
    }

    override fun closeApp() {
        config.close()
    }

    private fun selectLanguage() {
        driver.until { it.findElement(AppiumBy.id(KOREA_BTN)).click() }
    }

    private fun permissionAllow() {
        driver.until { it.findElement(AppiumBy.id(PERMISSION_LIST_ALLOW)).click() }
        driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_FOREGROUND_ONLY)).click() }
        driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
        driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
        driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
    }

    private fun gotoRatePage() {
        driver.until { it.findElement(AppiumBy.id(RATE_NAV_BTN)).click() }
    }
    companion object {
        private const val KOREA_BTN = "com.e9pay.remittance2:id/btn_ko"
        private const val PERMISSION_LIST_ALLOW = "com.e9pay.remittance2:id/btn_ok"
        private const val PERMISSION_ALLOW_FOREGROUND_ONLY = "com.android.permissioncontroller:id/permission_allow_foreground_only_button"
        private const val PERMISSION_ALLOW_BUTTON = "com.android.permissioncontroller:id/permission_allow_button"
        private const val RATE_NAV_BTN = "com.e9pay.remittance2:id/exchangeRate"
        private const val RATE = "com.e9pay.remittance2:id/currentExchangeRateContent"
    }
}