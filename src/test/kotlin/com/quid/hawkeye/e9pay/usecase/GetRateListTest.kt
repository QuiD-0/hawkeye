package com.quid.hawkeye.e9pay.usecase

import com.quid.hawkeye.appium.AppInfo
import com.quid.hawkeye.appium.AppiumConfig
import com.quid.hawkeye.appium.PhoneType.GALAXY_A30
import io.appium.java_client.AppiumBy
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class GetRateListTest {

    @Test
    fun `test execute`() {
        val config = AppiumConfig(GALAXY_A30, AppInfo.E9PAY)
        val driver = config.driver()

        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        selectLanguage(wait)
        permissionAllow(wait)

        config.close()
    }

    private fun selectLanguage(wait: WebDriverWait) {
        wait.until { it.findElement(AppiumBy.id(KOREA_BTN)).click() }
    }

    private fun permissionAllow(wait: WebDriverWait) {
        wait.until { it.findElement(AppiumBy.id(PERMISSION_LIST_ALLOW)).click() }
        wait.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_FOREGROUND_ONLY)).click() }
        wait.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
        wait.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
        wait.until { it.findElement(AppiumBy.id(PERMISSION_ALLOW_BUTTON)).click() }
    }

    companion object {
        private const val KOREA_BTN = "com.e9pay.remittance2:id/btn_ko"
        private const val PERMISSION_LIST_ALLOW = "com.e9pay.remittance2:id/btn_ok"
        private const val PERMISSION_ALLOW_FOREGROUND_ONLY = "com.android.permissioncontroller:id/permission_allow_foreground_only_button"
        private const val PERMISSION_ALLOW_BUTTON = "com.android.permissioncontroller:id/permission_allow_button"
    }
}