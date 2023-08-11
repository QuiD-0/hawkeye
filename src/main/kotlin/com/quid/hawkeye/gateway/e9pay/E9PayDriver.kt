package com.quid.hawkeye.gateway.e9pay

import com.quid.hawkeye.config.AppiumConfig
import com.quid.hawkeye.domain.AppInfo
import com.quid.hawkeye.domain.PhoneType
import com.quid.hawkeye.domain.Rate
import io.appium.java_client.AppiumBy
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.time.Duration

interface E9PayDriver {
    fun init(phone: PhoneType)
    fun selectLanguage()
    fun permissionAllow()
    fun gotoRatePage()
    fun close()
    fun getRateList(): List<Rate>

    @Component
    class E9PayDriverImpl: E9PayDriver {
        private lateinit var config: AppiumConfig
        private lateinit var driver: WebDriverWait

        override fun init(phone: PhoneType) {
            this.config = AppiumConfig(phone, AppInfo.E9PAY)
            this.driver = WebDriverWait(config.driver(), Duration.ofSeconds(10))
        }

        override fun selectLanguage() {
            driver.until { it.findElement(AppiumBy.id(KOREA_BTN)).click() }
        }

        override fun permissionAllow() {
            driver.until { it.findElement(AppiumBy.id(PERMISSION_LIST_ALLOW)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_FOREGROUND_ONLY)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
            driver.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
        }

        override fun gotoRatePage() {
            driver.until { it.findElement(AppiumBy.id(RATE_NAV_BTN)).click() }
        }

        override fun getRateList(): List<Rate> {
            return mutableListOf(Rate("E9PAY"))
        }

        override fun close() {
            config.close()
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
}