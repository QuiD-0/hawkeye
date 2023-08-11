package com.quid.hawkeye.e9pay.gateway.appium

import com.quid.hawkeye.app.App
import com.quid.hawkeye.app.AppInfo
import com.quid.hawkeye.config.AppiumConfig
import com.quid.hawkeye.app.PhoneType
import com.quid.hawkeye.e9pay.domain.E9Pay
import io.appium.java_client.AppiumBy
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class E9PayApp(phone: PhoneType) : App {
    private val config = AppiumConfig(phone, AppInfo.E9PAY)
    private val driver = WebDriverWait(config.driver(), Duration.ofSeconds(10))

    override fun initApp() {
        driver.also {
            selectLanguage()
            permissionAllow()
            gotoRatePage()
        }
    }

    override fun getRateList(): List<E9Pay> {
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