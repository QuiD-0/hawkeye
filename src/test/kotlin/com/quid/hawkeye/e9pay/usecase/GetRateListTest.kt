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
        val driver = AppiumConfig(GALAXY_A30, AppInfo.E9PAY).driver()

        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until{ driver.findElement(AppiumBy.xpath(LANGUAGE_SELECT)).text.also { println(it) } }

        driver.close()
    }

    companion object {
        private const val LANGUAGE_SELECT = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView[1]"
    }
}